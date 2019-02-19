import java.util.Scanner;
import java.io.*;
import java.net.*;

public class ClientObject {
    Socket socket;
    PrintWriter out;
    BufferedReader in, stdIn;


    ClientObject(String hostName, int portNumber){
        try{
            socket = new Socket(hostName, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            //System.out.println("connected");
            this.commandList();

        }
        catch(IOException e){
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);

        }
    }

    public void printOptionMenu(){
        System.out.println("");
        System.out.println("Pick from the following options: ");
        System.out.println("");
        System.out.println("p: put a question in the bank");
        System.out.println("d <n>: delete a question from the bank, <n> being the number");
        System.out.println("g <n>: get question number <n> from the bank");
        System.out.println("r: get a random question from the bank");
        System.out.println("c <n> <x>: check answer <x> to question <n> in the bank");
        System.out.println("k: kill - terminate the server");
        System.out.println("q: quit - terminate the client");
        System.out.println("h: help");
        System.out.println("");
    }

    public void commandList(){
        String userInput;
        try{
            while(true){
                printOptionMenu();
                System.out.print("> ");
                userInput = stdIn.readLine();
                

                if(userInput.substring(0,1).equals("p")){
                    out.println("p");
                    this.putNewquestion();
                }

                else if(userInput.substring(0,1).equals("d")){
                    //String questionNum = userInput.substring(2,3);
                    //out.println(userInput);
                    this.deleteQuestion(userInput);
                }

                else if(userInput.substring(0,1).equals("g")){
                    this.getQuestion(userInput);
                }

                else if(userInput.substring(0,1).equals("r")){
                    out.println("r");
                    this.getRandomQuestion();
                }

                else if(userInput.substring(0,1).equals("c")){
                    this.checkAnswer(userInput);
                }

                else if(userInput.substring(0,1).equals("k")){
                    System.out.println("Killing the server!");
                    out.println("k");
                    break;
                }

                else if(userInput.substring(0,1).equals("q")){
                    System.out.println("Killing the client!");
                    out.println("q");
                    break;
                }

                else if(userInput.substring(0,1).equals("h")){
                    this.printOptionMenu();
                }
            }

            System.out.println("Server or client was killed. Please reconnect to the server using the localport listed or restart the server to get the new port its listening on.");
        }
        catch(IOException e){
            System.out.println(e.getMessage());
            System.exit(1);

        }

    }

    public void putNewquestion(){
        try{
            String userInput = stdIn.readLine();
            out.println(userInput);
            //System.out.println("client: " + userInput);
            //System.out.println("back: " + in.readLine());
            //userInput = stdIn.readLine();
            String dotInput="";
            while(true){
                userInput = stdIn.readLine();
                //System.out.println("client in while loop: " + userInput);
                //userInput = stdIn.readLine();
                // if(userInput.equals(".") && dotInput.equals(".")){
                //     break;
                // }
                if(userInput.equals(".")){
                    out.println(userInput);
                    userInput = stdIn.readLine();
                    dotInput = userInput;
                    //System.out.println("dotinput " + userInput);
                    //System.out.println("client in period: " + userInput);
                    //out.println(userInput);
                    //System.out.println("back: " + in.readLine());
                    if(dotInput.equals(".")){
                        //System.out.println("we're breaking out");
                        out.println(dotInput);
                        break;
                    }
                    else{
                        out.println(userInput);
                        //System.out.println("client in else in period: " + userInput);
                        //System.out.println("back: " + in.readLine());
                    }
                    //continue;    
                }
                else{
                    out.println(userInput);
                    //System.out.println("client in else while: " + userInput);
                    //System.out.println("back: " + in.readLine());
                    //userInput = stdIn.readLine();
                }
                //out.println(userInput);
            }
            //System.out.println("before answer check");
            if((userInput = stdIn.readLine()) != null){
                //correct answer
                out.println(userInput);
                //System.out.println("back: " + in.readLine());
            }
            //System.out.println("uh");
            System.out.println(in.readLine());
            //System.out.println("questions array");
            //System.out.println(in.readLine());
            return;


            // if(in.readLine() != null){
            //     //question number
            //     System.out.println(in.readLine());
            // }
        }
        catch(IOException e){
            e.printStackTrace();
        }


    }

    public void deleteQuestion(String number){
        out.println(number);
        String message="";
        try{
            if((message = in.readLine())!= null){
                System.out.println(message);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public void getQuestion(String number){
        out.println(number);
        //boolean has;
        
        try{
            String message = in.readLine();
            //System.out.println("has? " + message);
            boolean has = Boolean.parseBoolean(message);

            if(has){            
                if((message = in.readLine())!= null){
                    System.out.println(message);
                }
                if((message = in.readLine())!= null){
                    System.out.println(message);
                }
                if((message = in.readLine())!= null){
                    //System.out.println(message);
                    int size = Integer.parseInt(message);
                    for(int i = 0; i< size; i++){
                        message = in.readLine();
                        System.out.println(message);
                    }
                }
            }
            else{
                message = in.readLine();
                System.out.println(message);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public void getRandomQuestion(){
        String text="";
        try{
            if((text = in.readLine())!= null){
                System.out.println(text);
            }
            if((text = in.readLine())!= null){
                System.out.println(text);
            }
            if((text = in.readLine())!= null){
                //System.out.println(text);
                int size = Integer.parseInt(text);
                for(int i = 0; i< size; i++){
                    text = in.readLine();
                    System.out.println(text);
                }
            }
            // while((text = in.readLine())!= null){
            //     System.out.print(text);
            // }
            // String userInput = stdIn.readLine();
            // out.println(userInput);
            // String message;
            // if((message = in.readLine())!= null){
            //     System.out.println(message);
            // }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void checkAnswer(String check){
        out.println(check);
        try{
            String message = in.readLine();
            System.out.println(message);
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
}
