package com.VL.Srapper;

//https://tyrus-project.github.io/documentation/1.12/user-guide.html#getting-started

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.DeploymentException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

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
        
        private WebClient client = new WebClient();
        String baseUrl = "https://www.mescoursescasino.fr/ecommerce/GC-catalog/fr/WE64904/?moderetrait=Z2" ;
        HtmlPage page = null;
        HtmlForm form = null;
        HtmlTextInput textField = null;
        
        
        @javax.websocket.OnClose
        public void onClose(javax.websocket.Session session, javax.websocket.CloseReason close_reason) {
            System.out.println("onClose: " + close_reason.getReasonPhrase());
        }

        @javax.websocket.OnError
        public void onError(javax.websocket.Session session, Throwable throwable) {
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
                
                
                textField.type(query);
                final HtmlButton button;
                button = (HtmlButton) page.getByXPath("//button[@title='OK']").get(0);
                page = button.click();
                List<HtmlSection> nodes = page.getByXPath("//section[@class=' tagClick']");
                System.out.println(nodes.get(0).getAttributeNode("data-product-name").getNodeValue());
                HtmlElement marque = page.getFirstByXPath("//strong[@class='color6 ']");
                System.out.println(marque.asText());
                
                //TODO : Remplir la liste produits avec qty produit de la recherche de query
                List<Produit> produits = new ArrayList<>();
                
                /*  TEST AJOUT PRODUIT  */
                List<String> images = new ArrayList<>();
                images.add("URL1");
                images.add("URL2");
                List<String> signaletique = new ArrayList<>();
                signaletique.add("URL_A");
                signaletique.add("URL_B");
                List<String> colone1 = new ArrayList<>();
                colone1.add("Informations nutritionnelles");
                colone1.add("valeur énergétique (kJ)");
                colone1.add("valeur énergétique (kcal)");
                List<String> colone2 = new ArrayList<>();
                colone2.add("100 g");
                colone2.add("~287 kJ");
                colone2.add("~68 kcal");
                List<List<String>> tabNutri = new ArrayList<>();
                tabNutri.add(colone1);
                tabNutri.add(colone2);
                Produit patate = new Produit("patate",  "Des patates",  1.5, 1.5,"1kg", images,signaletique,"Url_z",
                                "De l'amour et des calins", "pollens", "E3000, T69", "Froid", tabNutri);
                produits.add(patate);
                produits.add(patate);              
                /* Fin test */
                
                //Trasfert la list de produit dans une liste d'objets JSON
                List <JSONObject> jsonProduits = new ArrayList<>();
                produits.forEach((prod) -> {jsonProduits.add(prod.toJson());});
                
                JSONArray jProduits = new JSONArray(jsonProduits);  
                System.out.println("JSONArray.toString" + jProduits.toString());
                
                try{
                    session.getBasicRemote().sendText(jProduits.toString());
                } catch (IOException ex) {
                    Logger.getLogger(WebSockets.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        @javax.websocket.OnOpen
        public void onOpen(javax.websocket.Session session, javax.websocket.EndpointConfig ec) throws java.io.IOException {
            System.out.println("OnOpen... " + ec.getUserProperties().get("Author"));
            //session.getBasicRemote().sendText("{Handshaking: \"Yes\"}");
            try{
                client.getOptions().setCssEnabled(false);
                client.getOptions().setJavaScriptEnabled(false);
                page = (HtmlPage)client.getPage(baseUrl);
                form = (HtmlForm) page.getElementById("search");
                textField = form.getInputByName("query");
            }
            catch(FailingHttpStatusCodeException | IOException e){
                
            }
        }
    }

    public static void main(String[] args) {

        java.util.Map<String, Object> user_properties = new java.util.HashMap();
        user_properties.put("Author", "");

        org.glassfish.tyrus.server.Server server = new org.glassfish.tyrus.server.Server("localhost", 1963, "/FranckBarbier", user_properties /* or 'null' */, My_ServerEndpoint.class);
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


