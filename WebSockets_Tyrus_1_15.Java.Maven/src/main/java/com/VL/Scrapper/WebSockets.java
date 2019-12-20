package com.VL.Scrapper;

//https://tyrus-project.github.io/documentation/1.12/user-guide.html#getting-started

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.DeploymentException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import java.io.IOException;

public class WebSockets {

    /**
     * Danger : il faut que le constructeur de 'My_ServerEndpoint' soit bien
     * accessible par le serveur WebSockets. Ne pas oublier 'static'!
     */
    @javax.websocket.server.ServerEndpoint(value = "/WebSockets")
    public static class My_ServerEndpoint {

        Scrapper scrap = null;

        @javax.websocket.OnClose
        public void onClose(javax.websocket.Session session, javax.websocket.CloseReason close_reason) {
            System.out.println("onClose: " + close_reason.getReasonPhrase());
            System.out.println("Message onClose: " + close_reason.toString());
        }

        @javax.websocket.OnError
        public void onError(javax.websocket.Session session, Throwable throwable) {
            System.out.println(" message on Error = " + throwable.toString() );
            System.out.println("onError: " + throwable.getMessage());
        }

        @javax.websocket.OnMessage
        public void onMessage(javax.websocket.Session session, String message) throws IOException {
            JSONObject jMessage= new JSONObject(message);       //convertir le message en object JSON
            //System.out.println("jMessage.has(\"Response\")= " + jMessage.has("Response"));
            //System.out.println("jMessage.has(\"Request\")= " + jMessage.has("Request"));

            if ( jMessage.has("Response") ){

                System.out.println("Message de JavaScript :" + jMessage.get("Response"));

            }else if (jMessage.has("Request")){
                String query = jMessage.getString("Request");
                int qty = jMessage.getInt("Quantity");


                //TODO : Remplir la liste produits avec qty produit de la recherche de query
                List<Produit> produits = new ArrayList<>();

                try{
                    session.getBasicRemote().sendText(scrap.Search(query, qty).toString());
                } catch (IOException ex) {
                    Logger.getLogger(WebSockets.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        @javax.websocket.OnOpen
        public void onOpen(javax.websocket.Session session, javax.websocket.EndpointConfig ec) throws java.io.IOException {
            System.out.println("OnOpen... " + ec.getUserProperties().get("Author"));
            System.out.println("OnOpen string = " + ec.toString());
            //session.getBasicRemote().sendText("{Handshaking: \"Yes\"}");
            try{
                scrap = new Scrapper();
            }
            catch(FailingHttpStatusCodeException | IOException e){

            }
        }
    }

    public static void main(String[] args) {

        java.util.Map<String, Object> user_properties = new java.util.HashMap();
        user_properties.put("Author", "Lafon_Vanootegem");

        org.glassfish.tyrus.server.Server server = new org.glassfish.tyrus.server.Server("localhost", 1963, "/LafonVanootegem", user_properties /* or 'null' */, My_ServerEndpoint.class);
        try {
            server.start();
            // The Web page is launched from Java:
            java.awt.Desktop.getDesktop().browse(java.nio.file.FileSystems.getDefault().getPath("web" + java.io.File.separatorChar + "index.html").toUri());

            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
            System.out.println("Please press a key to stop the server...");
            reader.readLine();
        } catch (IOException | DeploymentException e) {
        } finally {
            server.stop();
        }
    }
}
