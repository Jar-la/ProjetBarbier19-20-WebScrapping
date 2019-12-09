package com.VL.Srapper;

//https://tyrus-project.github.io/documentation/1.12/user-guide.html#getting-started

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlForm;

import com.gargoylesoftware.htmlunit.html.*;
import java.io.IOException;



public class WebSockets {

    /**
     * Danger : il faut que le constructeur de 'My_ServerEndpoint' soit bien
     * accessible par le serveur WebSockets. Ne pas oublier 'static'!
     */
    @javax.websocket.server.ServerEndpoint(value = "/WebSockets_illustration")
    public static class My_ServerEndpoint {

        @javax.websocket.OnClose
        public void onClose(javax.websocket.Session session, javax.websocket.CloseReason close_reason) {
            System.out.println("onClose: " + close_reason.getReasonPhrase());
        }

        @javax.websocket.OnError
        public void onError(javax.websocket.Session session, Throwable throwable) {
            System.out.println("onError: " + throwable.getMessage());
        }

        @javax.websocket.OnMessage
        public void onMessage(javax.websocket.Session session, String message) {
            System.out.println("Message from JavaScript: " + message);
        }

        @javax.websocket.OnOpen
        public void onOpen(javax.websocket.Session session, javax.websocket.EndpointConfig ec) throws java.io.IOException {
            System.out.println("OnOpen... " + ec.getUserProperties().get("Author"));
            session.getBasicRemote().sendText("{Handshaking: \"Yes\"}");
            String baseUrl = "https://www.mescoursescasino.fr/ecommerce/GC-catalog/fr/WE64904/?moderetrait=Z2" ;
            WebClient client = new WebClient();
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);
            /*try{
            HtmlPage page = (HtmlPage)client.getPage(baseUrl);
            final HtmlForm form = (HtmlForm) page.getElementById("search");
            session.getBasicRemote().sendText(page.asXml());
            }
            catch(FailingHttpStatusCodeException | IOException e){
            }*/
            
            HtmlPage page = (HtmlPage)client.getPage(baseUrl);
            final HtmlForm form = (HtmlForm) page.getElementById("search");
            final HtmlTextInput textField = form.getInputByName("query");
            textField.type("Nutella");
            final HtmlButton button;
            button = (HtmlButton) page.getByXPath("//button[@title='OK']").get(0);
            page = button.click();
            
            session.getBasicRemote().sendText(page.asXml());
            
        }
    }

    public static void main(String[] args) {

        java.util.Map<String, Object> user_properties = new java.util.HashMap();
        user_properties.put("Author", "FranckBarbier");

        org.glassfish.tyrus.server.Server server = new org.glassfish.tyrus.server.Server("localhost", 1963, "/FranckBarbier", user_properties /* or 'null' */, My_ServerEndpoint.class);
        try {
            server.start();
            // The Web page is launched from Java:
            java.awt.Desktop.getDesktop().browse(java.nio.file.FileSystems.getDefault().getPath("web" + java.io.File.separatorChar + "index.html").toUri());

            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
            System.out.println("Please press a key to stop the server...");
            reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}
