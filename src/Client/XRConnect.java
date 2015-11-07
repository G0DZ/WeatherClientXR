package Client;


import Service.Weather;
import org.apache.xmlrpc.XmlRpcClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Vector;

public class XRConnect implements Runnable {
    static String serverIP = "127.0.0.1";
    static int serverPort = 9876;
    static int cityID = 0;
    private ClientFrameEngine parent;
    private Thread t;

    public XRConnect(String cityID, ClientFrameEngine parent) {
        this.cityID = Integer.parseInt(cityID);
        this.parent = parent;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        System.out.println("Инициализация клиента... ");
        try {
            XmlRpcClient service = new XmlRpcClient(serverIP, serverPort);

            Vector params = new Vector();
            params.addElement(cityID);
            Object res = service.execute("YWeather.getStringWeather", params);
            String result = (String) res;
            //System.out.println(result);
            String decoded = new String(
                    Base64.getDecoder().decode( result ),
                    StandardCharsets.UTF_8 );
            System.out.println( decoded );
            parent.analyzeAnswer(decoded);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}