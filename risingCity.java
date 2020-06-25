import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class risingCity {

    public static void main(String[] args) {
        try(BufferedWriter bufferedWriter=new BufferedWriter(
                new FileWriter(new File("output_file.txt")));){
            ReadFileHelper readFileHelper=new ReadFileHelper();                                 //to read the input file
            MinHeap minHeap=new MinHeap();
            RedBlackTree redBlackTree=new RedBlackTree();
            //read the input file and store all the commands inside a list named as command list
            List<String> commandsList =
                    readFileHelper.readFile(new File(
                            args[0]));

            /*initialize the  following variables :

            globalTime counter: global time which increments sequentially when the work is being done
            index :index of the command list which will increments with each command read from the list
            companyBusy: true , if the company is currently working on some building
            buildingAtWork : current building which is under constructio
            executed time: total executed time of a building under construction
            counter : counter to keep track of 5 seconds , will reset before picking up a new building for construction */

            int globalCounter=0;
            int index=0;
            boolean company_busy=false;
            Building buildingAtWork=null;
            int executed_time=0;
            int counter=0;


            while(true){
                if(index!=commandsList.size() && globalCounter==Integer.parseInt(commandsList.get(index).substring(0,commandsList.get(index).indexOf(":"))) ){
                    String command=commandsList.get(index);
                    String commmandType=command.substring(command.indexOf(":")+1,command.indexOf("("));

                    /*if the read command is an insert , insert the building inside min heap and red black tree and create a pointer
                    from min heap to red black tree node for the same building */

                    if(commmandType.equalsIgnoreCase("insert")){
                        int buildingNumber=Integer.parseInt(command.substring(command.indexOf("(")+1,command.indexOf(",")));
                        int totalExecutionTime=Integer.parseInt(command.substring(command.indexOf(",")+1,command.indexOf(")")));

                        Building building=new Building(buildingNumber,0,totalExecutionTime);
                        RedBlackNode redBlackNode = new RedBlackNode(new Building(buildingNumber,0,totalExecutionTime), null,
                                redBlackTree.getTNULL() ,
                                redBlackTree.getTNULL(), 1);

                        building.setRedBlackNode(redBlackNode);
                        redBlackTree.insert(redBlackNode);
                        minHeap.insertBuilding(building);

                     //if the command is print , write the output to the output file using file writer
                    }else if (commmandType.equalsIgnoreCase("printbuilding")){
                        //if the print command is just printing a particular building
                        if(command.indexOf(",")==-1){
                            int buildingNumber=Integer.parseInt(command.substring(command.indexOf("(")+1,command.indexOf(")")));
                            RedBlackNode redBlackNode = redBlackTree.searchTree(buildingNumber);
                            if(redBlackNode.getData()==null){
                                bufferedWriter.write("(0,0,0)");
                                bufferedWriter.newLine();
                            }else{
                                bufferedWriter.write("("+redBlackNode.getData().getBuildingNumber()+","+redBlackNode.getData().getExecutedTime()+","+
                                        redBlackNode.getData().getTotalTime()+")");
                                bufferedWriter.newLine();
                            }
                        // if the print command is  printing  building present in a particular range
                        }else{
                            long buildingNumber1=Integer.parseInt(command.substring(command.indexOf("(")+1,command.indexOf(",")));
                            long buildingNumber2=Integer.parseInt(command.substring(command.indexOf(",")+1,command.indexOf(")")));
                            List<Building> buildings = redBlackTree.searchTree(redBlackTree.getRoot(), buildingNumber1, buildingNumber2, new ArrayList<Building>());
                            if(buildings.size()==0){
                                bufferedWriter.write("(0,0,0)");
                                bufferedWriter.newLine();
                            }else{
                                for(int i=0;i<buildings.size();i++){
                                    if(i==buildings.size()-1){
                                        bufferedWriter.write("("+buildings.get(i).getBuildingNumber()+","+buildings.get(i).getExecutedTime()+","+
                                                buildings.get(i).getTotalTime()+")");
                                    }else{
                                        bufferedWriter.write("("+buildings.get(i).getBuildingNumber()+","+buildings.get(i).getExecutedTime()+","+
                                                buildings.get(i).getTotalTime()+")"+",");
                                    }
                                }
                                bufferedWriter.newLine();
                            }
                        }
                    }
                    index++;
                }else{
                    /* if the wayne company is free just the building with min execution time(or the min building number ,if execution time is same )
                    and start working on it */
                    if(!company_busy){
                        counter=0;
                        if(minHeap.getSize()!=0){
                            company_busy=true;
                            buildingAtWork=minHeap.getHeap()[0];
                            executed_time=buildingAtWork.getExecutedTime();  // get the current executed time and increment with 1 with each global time
                            minHeap.deleteMin();
                            executed_time++;
                            buildingAtWork.setExecutedTime(executed_time);
                            buildingAtWork.getRedBlackNode().getData().setExecutedTime(executed_time);
                            globalCounter++;
                            counter++;
                        }else{
                            globalCounter++;
                        }
                    }else{
                        //if the current building's construction is finished , just write it to the output file and hop to next building(with min exc time)
                        if (buildingAtWork.getExecutedTime()==buildingAtWork.getTotalTime()){
                            bufferedWriter.write("("+buildingAtWork.getBuildingNumber()+","+globalCounter+")");
                            bufferedWriter.newLine();
                            redBlackTree.deleteNode(buildingAtWork.getRedBlackNode());                        //remove from red black tree
                            if(minHeap.getSize()==0 ){
                                if(index==commandsList.size()){
                                    break;
                                }
                            }
                            company_busy=false;                                                                 // make company free to work on next building
                        }//if the buildingAtWork is not finished construction and company has not worked on it for five consecutive days
                        else if(counter<5 ){
                            executed_time++;
                            buildingAtWork.setExecutedTime(executed_time);
                            buildingAtWork.getRedBlackNode().getData().setExecutedTime(executed_time);
                            globalCounter++;
                            counter++;
                        }else if(counter==5){   //If the company has worked on a building for 5 days , it wil hop on next building with min execution time
                            minHeap.insertBuilding(buildingAtWork);
                            company_busy=false;
                        }
                    }

                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
