import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Node extends JButton implements ActionListener {
    Node parent;
    int col;
    int row;
    int gCost;
    int hCost;
    int fCost;
    boolean start;
    boolean goal;
    boolean solid;
    boolean open;
    boolean checked;

    public Node(int col,int row){
        this.col=col;
        this.row=row;

        setBackground(Color.BLACK);
        // setForeground(Color.black);


        addActionListener(this);

    }


    public void setAsStart(){
        setBackground(Color.red);
        setForeground(Color.white);
        setText("Start");
        start=true;
    }

    public void setAsGoal(){
        setBackground(Color.blue);
        setForeground(Color.white);
        setText("Goal");
        goal=true;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        setBackground(Color.green);
    }
    


}
