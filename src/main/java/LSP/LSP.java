package LSP;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class LSP {
    private static final int STACKMAX = 100;
    private static final int LENNAME = 200;
    private static double ESt[]; 
    private static double WSt[]; 
    private static int StPtr;
    
    private static String InFileName;
    private static String OutFileName;

    private static String Id;
    private static double rV, W;
    private static double CN;
    private static double xmin=0;
    private static double xmax=0;
    
    private static File input = null;
    private static FileWriter output = null;
    private static FileReader fr = null;
    private static BufferedReader br = null;
    private static PrintWriter pw = null;
    private static StringTokenizer st = null;
    
    public LSP() {
        ESt = new double[STACKMAX];
        WSt = new double[LENNAME];
    }


    /**
     * Asigna xmin = valor 'x[i] menor' y xmax = 'x[i] mayor' 
     * @param n
     * @param x 
     */
    public static void minmax (int n, double x[]){ // double xmin, double xmax){
      xmin = x[0];
      xmax = xmin;
      for(int i=1; i<n; i++)
	 if (x[i] < xmin)
	     xmin = x[i];
	 else
	     if (x[i] > xmax) xmax = x[i];
    }
    
    public static double plog(double t){
      return t * ( 1. - t * ( 0.5 - t / 3. ) );
    }
    
    /** 
     *--------- WEIGHTED POWER MEAN FUNCTION-----------------------|
     *                                                             |
     *    Xmean = [W[0]*x[0]**r + ... + W[n-1]*x[n-1]**r] ** (1/r) |
     *    x[0 .. n-1] = input values (preferences)                 |
     *    W[0 .. n-1] = positive weights; W[0]+...+W[n-1] = 1      |
     *    r           = exponent (any real value)                  |
     *-------------------------------------------------------------|
     * 
     * @param n cardinalidad del dominio
     * @param W ponderaciones de cada entrada e n
     * @param r el valor posta
     * @param x el valor de las variables
     * @return 
     */
    public static double wpm (int n, double W[], double r, double x[]){
      double rmin = 1.E-16, rmax = 1.E+16, small = 1.E-9;
      double xminlog, xmaxlog, h, xmean;
      int i;

      minmax (n, x);  //, xmin, xmax );

      if ( r < -rmax )
	 xmean = xmin;
      else if (r < -rmin)
      {
	 xmean = 0.;
	 if ( xmin > 0. )
	 {
	    xminlog = Math.log(xmin);
            for(i=0; i<n; i++)
            {
               h = r * ( Math.log(x[i]) - xminlog ); // Multiplica el r * logaritmo de el valor actual - logaritmo del valor menor
               xmean += W[i] * (1. + Math.exp(h)) * Math.tanh(h/2.); // actualiza xmean pesado con W[i] y multiplicado por exp .h y tanh.h ... 
            }
            if ( Math.abs(xmean) > small) //Decide si hacer log o plog .. no se que hace el plog
               xmean = Math.exp( xminlog + Math.log(1. + xmean)/r );
            else
               xmean = Math.exp( xminlog + plog(xmean)/r );
         }
      }
      else if (r <= rmin)
      {
         xmean = 0.;
         if ( xmin > 0. )
         {
            for(i=0; i<n; i++) 
                xmean += W[i]*Math.log(x[i]); //saca una media mas poronga
            xmean = Math.exp(xmean);
         }
      }
      else if (r <= rmax)
      {
         xmean = 0.;
         if ( xmax > 0. )
         {
            xmaxlog = Math.log(xmax);
            for(i=0; i<n; i++)
               if (x[i] > 0.)
               {
                  h = r * ( Math.log(x[i]) - xmaxlog );
                  xmean += W[i] * (1. + Math.exp(h)) * Math.tanh(h/2.); //ajustaria el limite con el h
	       }
               else xmean -= W[i];

            if ( Math.abs(xmean) > small)  // segun el limite si no es muy pequeño usalog o plog ... 
               xmean = Math.exp( xmaxlog + Math.log(1. + xmean)/r );
            else
               xmean = Math.exp( xmaxlog + plog(xmean)/r );
         }
      }
      else             //     r > Rmax
         xmean = xmax; 
      return xmean;
    }
    
    public static void pushInStack(){
        System.out.println(Id+" - "+CN+" - "+rV+" - "+W);
        pw.write(Id+"\t"+CN+"\t\t"+rV+"\t\t"+W+"\n");
        StPtr++;
        ESt[StPtr] = rV;		//Push Value of Elementary Preference
        WSt[StPtr] = W;			//Push W
    } // end of PushInStack
    
    
    public static void popStack(){
        double[] WAr = new double[STACKMAX];
        double[] EAr = new double[STACKMAX];
        int i;

	for (i = 0; i < CN ; i++){ //Popea segun el tamaño del Dominio del Operador n veces
		EAr[i] = ESt[StPtr - i];
		WAr[i] = WSt[StPtr - i];
        }
        StPtr = (int) (StPtr - CN + 1); //actualiza el puntero
        ESt[StPtr] = wpm((int) CN, WAr, rV, EAr); 	// Push resultant GCD // pushea el resultado
        WSt[StPtr] = W;	// Push W
        System.out.println(Id+" - "+CN+" - "+rV+" - "+W+" - "+ESt[StPtr]);
        pw.write(Id+"\t"+CN+"\t\t"+rV+"\t\t"+W+"\t"+ESt[StPtr]+"\n"); //escribe el resultado 
    } // end of PopStack
    
    public static void header(){
        
        pw.write("Input File Name:%s\n");
	pw.write("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
	pw.write("Ident.\tCode-#Opnd.\tE.C.-r\t\tWeight\n");
	pw.write("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

   } // end of Header

    
    public void main(String[] args) throws IOException {

        InFileName ="D:\\Documentos\\Area\\LSP\\InExp.txt";
        OutFileName ="D:\\Documentos\\Area\\LSP\\sal.txt";
        ESt = new double[STACKMAX];
        WSt = new double[LENNAME];
        try{
            input = new File(InFileName);
            output = new FileWriter(OutFileName);
            
            fr = new FileReader(input);
            pw = new PrintWriter(output);
          
            br = new BufferedReader(fr);            
            StPtr = -1;
            header();
            String linea;
            while((linea=br.readLine())!=null){
                st = new StringTokenizer(linea);
                String t[] = new String[4];
                int i=0;
                while (st.hasMoreTokens())
                    t[i++] = st.nextToken();

                Id = t[0];
                CN = Double.parseDouble(t[1]);
                rV = Double.parseDouble(t[2]); //LA POSTA
                W = Double.parseDouble(t[3]);
                
                if (Id.contentEquals("r")) //Operador
                    popStack();
                else
                    if (Id.contentEquals("c")) //Operando
                        pushInStack();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if (null!= fr){
                    fr.close();
                    pw.close();
                }
            }catch(Exception e2){
                e2.printStackTrace();
            }
        }
}
}
