import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;


public class DemoPanel extends JPanel {
    final int maxCol=15;
    final int maxRow=10;
    final int nodeSize=70;
    final int screenWidth=nodeSize*maxCol;
    final int screenHeight=nodeSize*maxRow;


    //Nodes in panel
    Node[][] node=new Node[maxCol][maxRow];

    Node startNode,goalNode,currentNode;

    public DemoPanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.white);
        this.setLayout(new GridLayout(maxRow,maxCol));

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

        setAsStartNode(3,6);
        setAsGoalNode(11,3);
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
}
