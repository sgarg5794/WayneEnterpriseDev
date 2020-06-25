
/*Pojo class for building implementing comparable interface to compare the two building on the basis of execution time and
building number*/

public class Building implements Comparable {

    long buildingNumber;
    int executedTime;
    int totalTime;
    RedBlackNode redBlackNode;

    public RedBlackNode getRedBlackNode() {
        return redBlackNode;
    }

    public void setRedBlackNode(RedBlackNode redBlackNode) {
        this.redBlackNode = redBlackNode;
    }

    public Building(long buildingNumber, int executedTime, int totalTime){
        this.buildingNumber=buildingNumber;
        this.executedTime=executedTime;

        this.totalTime=totalTime;
    }


    public long getBuildingNumber() {
        return buildingNumber;
    }

    public int getExecutedTime() {
        return executedTime;
    }

    public void setExecutedTime(int executedTime) {
        this.executedTime = executedTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

    @Override
    public int compareTo(Object o) {
        Building building=(Building)o;

        if(this.executedTime<building.getExecutedTime()){
            return -1;
        }else if(this.executedTime==building.getExecutedTime()){
            if(this.buildingNumber<building.getBuildingNumber()){
                return -1;
            }
        }
        return 1;
    }
}
