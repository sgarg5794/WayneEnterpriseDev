
//Java Implementation of Min Heap

public class MinHeap {

    //Array representing the heap containing the building objects
    private Building heap[];
    //current size of the filled Heap array
    private int size;
    //Max Capacity of Heap Array
    private static final int MAX_SIZE=2000;

    public MinHeap(){
        this.heap=new Building[MAX_SIZE];
        this.size=0;
    }

    //Insert function to insert new building into the heap
    public void insertBuilding(Building building){
        if(size>=MAX_SIZE){                                                //If the heap array is full , return
            return;
        }else{
        heap[size]=building;                                               //insert the node as the last child and then heap up to maintain heap property
            int current=size;
            if(size!=0){
                while(heap[current].compareTo(getParent(current))==-1){    //loop until the child(current) is greater than the parent
                    swap(heap[current],getParent(current),current,(current-1)/2);     //swap the parent node and current(child) node
                    current=(current-1)/2;                                                    //get the parent of current and repeat the loop
                    if(getParent(current)==null){                                             //After the current node is compared with root , break
                        break;
                    }
                }
            }
            size++;
        }
    }

    //Get the parent of the node
    private Building getParent(int indexOfChild){
        if(indexOfChild==0){
            return null;
        }
        return heap[(indexOfChild-1)/2];
    }

    //function to delete the min (first element of heap array)
    public void deleteMin(){
        if(size==0){
            return;
        }
        Building last=heap[size-1];
        size--;
        heap[0]=last;
        heapify(0);             //After the element has been removed , we will perform heapify to maintain the heap property
    }

    private void heapify(int pos) {
        if(isLeafNode(pos)){
            return ;
        }
        while(!isLeafNode(pos) && (heap[getRightChild(pos)].compareTo(heap[pos])==-1 ||
                heap[getLeftChild(pos)].compareTo(heap[pos])==-1)){
            int min=getMin(getRightChild(pos),getLeftChild(pos));
            swap(heap[pos],heap[min],pos,min);
            pos=min;
        }

    }


    // Check if the node is leaf node(using n external nodes and n-1 internal nodes concept)
    private boolean isLeafNode( int pos) {
        if (pos >= (size / 2) && pos <=size) {
            return true;
        }
        return false;
    }

    //Get the minimum of two building(by comparing the building number and execution time
    private int getMin(int building, int building1) {
        if(heap[building].compareTo(heap[building1])==-1){
            return building;
        }
        return building1;
    }

    //Swap function to swap the building in order to satify heap property
    private void swap(Building parent,Building child,int parentpos,int childpos){
        Building temp=heap[parentpos];
        heap[parentpos]=heap[childpos];
        heap[childpos]=temp;
    }

    //get the right child from the heap array using the formula :(2*pos)+2 , where pos if the current index of the node whose right child we need
    private int getRightChild(int pos){
        return (2*pos)+2;
    }

    //get the left child from the heap array using the formula :(2*pos)+1 , where pos if the current index of the node whose left child we need
    private int getLeftChild(int pos){
        return (2*pos)+1;
    }

    public Building[] getHeap() {
        return heap;
    }

    public int getSize(){
        return size;
    }

}
