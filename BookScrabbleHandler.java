package test;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BookScrabbleHandler implements ClientHandler {
        DictionaryManager dictionaryManager = null;
        Scanner in;
        PrintWriter out;

        public BookScrabbleHandler() {
            this.dictionaryManager = DictionaryManager.get();
        }

        @Override
        public void handleClient(InputStream inFromClient, OutputStream outToClient) {
            try {
                in = new Scanner(inFromClient);
                out = new PrintWriter(outToClient);
                String[] arguments = in.next().split(",");
                ArrayList<String> argsList = new ArrayList<>(Arrays.asList(arguments));
                argsList.remove(0);
                boolean result = false;
                if (!arguments[0].equals("Q") && !arguments[0].equals("C")) {
                    out.println("Query Failed");
                }

                else if (arguments[0].equals("Q")) {
                    String[] query = argsList.toArray(new String[argsList.size()]);
                    result = dictionaryManager.query(query);
                }

                else if (arguments[0].equals("C")) {
                    String[] challenge = argsList.toArray(new String[argsList.size()]);
                    result = dictionaryManager.challenge(challenge);
                }

                if (result) {
                    out.println("true");
                } else {
                    out.println("false");
                }
                this.close();


            }catch (Exception e){
                throw new RuntimeException(e);
            }

        }

        @Override
        public void close() {
            try {
                out.flush();
                in.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }