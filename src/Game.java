import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Game extends JFrame implements ActionListener{
      int num,i,j,status=-1,count=0,breaker,turn=1,r;
      int a[][] = new int[3][3];
      int b[][] = new int[6][2];
      Random rand=new Random();
      Checker c = new Checker();
      JButton key[][]= new JButton[3][3];
      ImageIcon icon,x,o,win,lose,draw,logo;
      MainFrame f;
      Game(MainFrame f){
            this.f=f;
            for(i=0;i<=2;i++){
                  for(j=0;j<=2;j++){
                        a[i][j]=-1;
                  }
            }
            icon = new ImageIcon("assets/blank.png");
            x =new ImageIcon("assets/x.png");
            o =new ImageIcon("assets/o.png");
            win =new ImageIcon("assets/win.png");
            draw =new ImageIcon("assets/draw.png");
            lose =new ImageIcon("assets/lose.png");
            logo =new ImageIcon("assets/logo.png");
            //buttons
            for(i=0;i<=2;i++){
                  for(j=0;j<=2;j++){
                        key[i][j] = new JButton(icon);key[i][j].addActionListener(this);
                  }
            }
            //main window
            setTitle("TIC-TAC-TOE");
            setLayout(new GridLayout(3,3,5,5));
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(600,600);
            setResizable(false);
            setIconImage(logo.getImage());
            for(i=0;i<=2;i++){
                  for(j=0;j<=2;j++){
                        add(key[i][j]);
                  }
            }
            setVisible(true);
      }
      void gameEnd(){
            if(status==1){JOptionPane.showMessageDialog(this, "YOU WIN", "Victory!",JOptionPane.INFORMATION_MESSAGE, win);}
            if(status==0){JOptionPane.showMessageDialog(this, "YOU LOSE", "Defeat!",JOptionPane.INFORMATION_MESSAGE, lose);}
            if(status==2){JOptionPane.showMessageDialog(this, "IT'S DRAW", "Draw",JOptionPane.INFORMATION_MESSAGE, draw);}
            f.score_status(status);
            dispose();
      }
      void computerTurn(){breaker=0;num=0;
            for(i=0;i<=2;i++){
                  for(j=0;j<=2;j++){
                        if(a[i][j]==-1){
                              if(c.check(i,j,a)==0){
                                    a[i][j]=0;breaker=1;break;
                              }
                              else if(c.check(i,j,a)==2||c.check(i,j,a)==-1){
                                    b[num][0]=i;b[num][1]=j;num++;
                              }
                        }
                  }
                  if(breaker==1){turn++;break;}
                  if(i==2&&num>0){
                        num=rand.nextInt(num);
                        i=b[num][0];j=b[num][1];a[i][j]=0;turn++;break;
                  }
                  if(i==2&&num==0){
                        i=rand.nextInt(3);j=rand.nextInt(3);
                        while(a[i][j]==1||a[i][j]==0){i=rand.nextInt(3);j=rand.nextInt(3);}
                        a[i][j]=0;turn++;break;
                  }
            }
      }
      void secTurn(){
            i=rand.nextInt(3);j=rand.nextInt(3);
            while(a[i][j]==1||a[i][j]==0){
                  i=rand.nextInt(3);j=rand.nextInt(3);
            }a[i][j]=0;status=-1;turn++;
      }
      public void actionPerformed(ActionEvent e){
            int q,z;
            for(q=0;q<=2;q++){
                  for(z=0;z<=2;z++){
                        if((e.getSource()==key[q][z])&&(a[q][z]==-1)){
                              a[q][z]=1;turn++;display();
                              status = winstatus(a);
                              if(status!=-1){gameEnd();return;}
                              if(turn == 2){secTurn();}
                              else if(turn % 2 == 0){computerTurn();}
                              display();status=winstatus(a);
                              if(status!=-1){gameEnd();}
                        }
                  }
            }
      }
      void display(){
            for(i=0;i<=2;i++){
                  for(j=0;j<=2;j++){
                        if(a[i][j]==1){key[i][j].setIcon(x);}
                        if(a[i][j]==0){key[i][j].setIcon(o);}
                  }
            }
      }
      static int winstatus(int a[][]){
            int i,j,count;
            //win
            for(i=0;i<=2;i++){if(a[0][i]==1&&a[1][i]==1&&a[2][i]==1){return 1;}}
            for(i=0;i<=2;i++){if(a[i][0]==1&&a[i][1]==1&&a[i][2]==1){return 1;}}
            if(a[0][0]==1&&a[1][1]==1&&a[2][2]==1){return 1;}
            if(a[0][2]==1&&a[1][1]==1&&a[2][0]==1){return 1;}
            //lose
            for(i=0;i<=2;i++){if(a[0][i]==0&&a[1][i]==0&&a[2][i]==0){return 0;}}
            for(i=0;i<=2;i++){if(a[i][0]==0&&a[i][1]==0&&a[i][2]==0){return 0;}}
            if(a[0][0]==0&&a[1][1]==0&&a[2][2]==0){return 0;}
            if(a[0][2]==0&&a[1][1]==0&&a[2][0]==0){return 0;}
            //draw
            count=0;
            for(i=0;i<=2;i++){
                  for(j=0;j<=2;j++){
                        //the game is still on
                        if(a[i][j]==-1){return -1;}
                        count++;
                        //draw
                        if(count==9){return 2;}
                  }
            }return -1;
      }
}
class Checker{
      int s,b,c,dnum=0,num=0;
      int check(int i,int j,int a[][]){
            a[i][j]=0;if(Game.winstatus(a)==0){a[i][j]=-1;return 0;}
            for(b=0;b<=2;b++){
                  for(c=0;c<=2;c++){
                        if(a[b][c]==-1){
                              a[b][c]=1;s=Game.winstatus(a);a[b][c]=-1;
                              if(s==0){a[i][j]=-1;return 0;}
                              if(s==1){a[i][j]=-1;return 1;}
                              if(s==-1){num++;}
                              if(s==2){dnum++;}
                        }
                  }
            }
            if(dnum>num){a[i][j]=-1;return 2;}
            else{a[i][j]=-1;return -1;}
      }

}

