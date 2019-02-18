import java.util.Scanner;
import java.io.*;
import java.net.*;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*;
 

public class ServerObject{
    ServerSocket serverSock;
    PrintWriter out;
    BufferedReader in;
    JSONArray questions;

    ServerObject(){
        try{
            serverSock = new ServerSocket(0);
            System.out.println("listening on port: " + serverSock.getLocalPort());
            File myFile = new File("Questions.json");
            questions = new JSONArray();
            if(myFile.exists()){
                Object obj = new JSONParser().parse(new FileReader(myFile));
                JSONObject json = (JSONObject) obj;
                questions = (JSONArray) json.get("questionBank");
            }
            // Socket clientSocket = serverSock.accept();
            // out = new PrintWriter(clientSocket.getOutputStream(), true);                   
            // in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.acceptCommands();
            

        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        catch(ParseException ex){
            ex.printStackTrace();
        }
        // catch(FileNotFoundException ex){
        //     ex.printStackTrace();
        // }



    }


    private void acceptCommands(){
        String command;
        try{
            while(true){
                Socket clientSocket = serverSock.accept();
                System.out.println("Connected!");
                out = new PrintWriter(clientSocket.getOutputStream(), true);                   
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                command = in.readLine();
                if(command.equals("p")){
                    this.putNewQuestion();
                }

                else if(command.substring(0,1).equals("d")){
                    int questionNum= Integer.parseInt(command.substring(2,3));
                    this.deleteQuestion(questionNum);
                    
                }

                else if(command.substring(0,1).equals("g")){
                    int questionNum = Integer.parseInt(command.substring(2,3));
                    this.getQuestion(questionNum);
                }

                else if(command.equals("r")){
                    this.randomQuestion();
                }

                else if(command.substring(0,1).equals("c")){
                    int questionNum = Integer.parseInt(command.substring(2,3));
                    String check = command.substring(4,5);
                    this.checkAnswer(questionNum, check);
                }

                else if(command.substring(0,1).equals("k")){
                    System.out.println("Killing the server!");
                    out.println("k");
                    break;
                }

            }
        }

        catch(IOException e){
            e.printStackTrace();
        }
    }

    private void deleteQuestion(int number){

    }

    private int doesQuestionExist(int number){
        JSONObject obj;
        int size = questionSize();
        for(int i = 0; i<size; i++){
            obj = (JSONObject) questions.get(i);
            int check = Integer.parseInt((obj.get("questionNumber")).toString());
            if(check == number){
                return number;
            }
        }
        return -1;
    }

    private void getQuestion(int number){
        // int size1 = questionSize();
        // String message = Integer.toString(size1);
        // out.println(message);
        // if(number == 0 || number > size1){
        //     out.println("Error, question " + number + " not found.");
        //     return;
        // }
        boolean has = hasQuestion(number);
        //out.println(has);
        if(has){
            String message = Boolean.toString(has);
            out.println(message);
            JSONObject obj = (JSONObject) questions.get(number-1);
            //PRINT IT OUT
            out.println(obj.get("tag"));
            out.println(obj.get("question"));
            JSONArray answers = (JSONArray) obj.get("answers");
            int size = answers.size();
            out.println(size);
            for(int i = 0; i<size; i++){
                out.println(answers.get(i));
            }
        }
        else{
            String message = Boolean.toString(has);
            out.println(message);
            out.println("Error, question " + number + " not found.");
        }


    }

    private boolean hasQuestion(int num){
        int check = doesQuestionExist(num);
        if(check != -1){
            return true;
        }
        return false;

    }

    private void randomQuestion(){

    }

    private void checkAnswer(int number, String check){
        
    }

    private int questionSize(){
        return questions.size();
    }

    private void updateJsonFile(){

        try (FileWriter file = new FileWriter("Questions.json")) 
        {
            JSONObject obj = new JSONObject();
            obj.put("questionBank",questions);
			file.write(obj.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + questions);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        // try(FileWriter fw = new FileWriter("Questions.json",true);
        //     BufferedWriter bw = new BufferedWriter(fw);
        //     PrintWriter pw = new PrintWriter(bw))
        //     {
        //     pw.write(questions.toJSONString());
        //     pw.flush(); 
        //     pw.close();
        //     }
        //     catch(IOException e){
        //                 e.printStackTrace();
        //     }

    }

    private void putNewQuestion(){
        //out.println("putting new question server");
        JSONObject question = new JSONObject();
        JSONArray answers = new JSONArray();
        String userinput;
        try{
            if((userinput = in.readLine()) != null){
                question.put("tag", userinput);
                // out.println(userinput);
                out.println("tag");
            }
            if((userinput = in.readLine()) != null){
                question.put("question", userinput);
                out.println("question");
            }
            String dotinput = ""; 
            while(true){
                userinput = in.readLine();
                if(userinput.equals(".") && dotinput.equals(".")){
                    question.put("answers",answers);
                    break;
                }
                dotinput = userinput;
                    //userinput = in.readLine();
                if(userinput.equals(".")){
                    continue;
                }
                out.println("answers");
                answers.add(userinput);
            }

            if((userinput = in.readLine()) != null){
                question.put("correctanswer",userinput);
                out.println("correctanswer");
            }
            // questionNum++;
            // question.put("questionNumber",questionNum);
            // out.println(questionNum);
            
            //int questionNumber = questionSize() + 1;
            if(questionSize() == 0){
                question.put("questionNumber", 1);
            }
            else if(questionSize() > 0){
                //grab last question and add 1
                int qNum = 1 + Integer.parseInt(questions.get(questionSize()-1));
                question.put("questionNumber", qNum);
            }
            //question.put("questionNumber", questionNumber);
            questions.add(question);
            //int numberQuestion = questions.size();
            out.println(questionSize());
            out.println(questions);
            this.updateJsonFile();
            return;


            }
            catch(IOException e){
                System.out.println(e.getMessage());
            }
            

            // PrintWriter pw = new PrintWriter(".json"); 
            // pw.write(question.toJSONString()); 
          
            // pw.flush(); 
            // pw.close();

            //now i gotta write this question to the json file 




    }


    
    
}