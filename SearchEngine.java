import java.io.IOException;
import java.net.URI;
import java.util.*;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    
    ArrayList<String> l = new ArrayList<>();
    
    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return createString(l);
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    l.add(parameters[1]);
                    return "String " + parameters[1] + " was added!";
                }
            }
            else if(url.getPath().contains("/search")){
                String[] parameters = url.getQuery().split("=");
                ArrayList<String> con = new ArrayList<>();
                
                if (parameters[0].equals("s")) {
                    for(String s: l){
                        if(s.contains(parameters[1])){
                            con.add(s);
                        }
                    }
                    return createString(con);
                }
            }
            return "404 Not Found!";
        }
    }
    public String createString(ArrayList<String> str){
        StringBuilder sb = new StringBuilder();
        if(str.size()>0){
            for(String s: str){
                sb.append(s);
                sb.append(" ");
            }
        }
        else{
            sb.append("Your list is empty! Please add some strings!");
        }
        return sb.toString();
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
