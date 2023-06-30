import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;


public class DemoPanel extends JPanel {
    final int maxCol=15;
    final int maxRow=10;
    final int nodeSize=70;
    final int screenWidth=nodeSize*maxCol;
    final int screenHeight=nodeSize*maxRow;
    


    //Nodes in panel
    Node[][] node=new Node[maxCol][maxRow];
    ArrayList<Node> openList=new ArrayList<>();
    ArrayList<Node> checkedList=new ArrayList<>();


    //Other
    boolean goalReached=false;
    int step=0;


    Node startNode,goalNode,currentNode;

    public DemoPanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.white);
        this.setLayout(new GridLayout(maxRow,maxCol));
        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);

        int col=0;
        int row=0;

        while (col<maxCol && row<maxRow) {
            node[col][row]=new Node(col,row);
            this.add(node[col][row]);
            
            col++;
            if (col==maxCol) {
                col=0;
                row++;
                
            }
        }

        setAsStartNode(11,3);
        setAsGoalNode(1,3);

        setSolidNode(3, 2);
        setSolidNode(2, 3);
        setSolidNode(2, 4);
        setSolidNode(3, 5);
        setSolidNode(3,6);

        //SET COST
        setCostOnNodes();
    }

    private void setAsStartNode(int col,int row){
        node[col][row].setAsStart();
        startNode=node[col][row];
        currentNode=startNode;
    }

    private void setAsGoalNode(int col,int row){
        node[col][row].setAsGoal();
        goalNode=node[col][row];
    }

    private void setSolidNode(int col,int row){
        node[col][row].setAsSolid();
    }

    private void setCostOnNodes(){
        int row=0;
        int col=0;

        while (col<maxCol && row<maxRow ) {
            getCost(node[col][row]);
            col++;
            if (col==maxCol) {
                col=0;
                row++;
            }
        }
    }

    private void getCost(Node node){
        //G COST (DISTANCE FROM START NODE)
        int xDistance=Math.abs(node.col-startNode.col);
        int yDistance=Math.abs(node.row-startNode.col);

        node.gCost=xDistance+yDistance;

         //HCOST (DISTANCE FROM START NODE)
         xDistance=Math.abs(node.col-goalNode.col);
         yDistance=Math.abs(node.row-goalNode.col);
        
        node.hCost=xDistance+yDistance;


        //FCOST (DISTANCE FROM START NODE)
        node.fCost=node.gCost+node.hCost;


        //DISPLAY FCOST ON NODE
        if (node!= startNode &&  node!=goalNode) {
            node.setText("<html>G:"+node.gCost+"<br>F:"+node.fCost+"<br>H:"+node.hCost+"</html>");
        }
    }


    public void search(){
        if (goalReached==false) {
            int row=currentNode.row;
            int col=currentNode.col;


            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openList.remove(currentNode);

            //open the North node
            if (row-1>=0) {
            openNode(node[col][row-1]);
            }

            //open the East node
            if (col-1>=0) {
            openNode(node[col-1][row]);
            }

            //open the west node
            if (col+1<maxCol) {
            openNode(node[col+1][row]);
            }

            //open the south node
            if (row+1<maxRow) {
            openNode(node[col][row+1]);
            }

            int bestNodeIndex=0;
            int bestNodefcost=999;

            for (int i = 0; i < openList.size(); i++) {
                //CHECK IF THIS NODES FCOST IS LOWER
                if (openList.get(i).fCost<bestNodefcost) {
                    bestNodeIndex=i;
                    bestNodefcost=openList.get(i).fCost;
                }

                //IF FCOST IS EQUAL CHECK THE GCOST
                else if (openList.get(i).fCost==bestNodefcost) {
                    if (openList.get(i).gCost<openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex=i;
                    }
                }
            }

            //AFTER LOOP WE GET THE BEST NODE WHICH IS OUR NEXT STEP
            currentNode=openList.get(bestNodeIndex);

            if (currentNode==goalNode) {
                goalReached=true;
                trackPath();
            }


        }
    }

    public void autoSearch(){
        while (goalReached==false && step<300) {
            int row=currentNode.row;
            int col=currentNode.col;


            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openList.remove(currentNode);

            //open the North node
            if (row-1>=0) {
            openNode(node[col][row-1]);
            }

            //open the East node
            if (col-1>=0) {
            openNode(node[col-1][row]);
            }

            //open the west node
            if (col+1<maxCol) {
            openNode(node[col+1][row]);
            }

            //open the south node
            if (row+1<maxRow) {
            openNode(node[col][row+1]);
            }

            int bestNodeIndex=0;
            int bestNodefcost=999;

            for (int i = 0; i < openList.size(); i++) {
                //CHECK IF THIS NODES FCOST IS LOWER
                if (openList.get(i).fCost<bestNodefcost) {
                    bestNodeIndex=i;
                    bestNodefcost=openList.get(i).fCost;
                }

                //IF FCOST IS EQUAL CHECK THE GCOST
                else if (openList.get(i).fCost==bestNodefcost) {
                    if (openList.get(i).gCost<openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex=i;
                    }
                }

            }

            //AFTER LOOP WE GET THE BEST NODE WHICH IS OUR NEXT STEP
            currentNode=openList.get(bestNodeIndex);

            if (currentNode==goalNode) {
                goalReached=true;
                trackPath();
            }

 
            step++;

        }
    }

    private void openNode(Node node){
        //if node not opened, add it to open list
        if (node.open==false && node.checked==false && node.solid==false) {
            node.setAsOpen();
            node.parent=currentNode;
            openList.add(node);
        }
    }

    public void trackPath(){
        //BACK TRACK TO FIRST NODE
        Node current=goalNode;
        while (current!=startNode) {
            current=current.parent;

            if (current!=startNode) {
                current.setAsPath();
            }
        }
    }
}
