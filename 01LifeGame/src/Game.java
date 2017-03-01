import Controller.LCon;
import Model.LMod;
import View.LVie;

class Game
{
    private int width = 15;
    private int height = 10;
    private int length = 25;
    private int border = 1;

    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                Game game = new Game();
                game.starting();
            }
        });
    }


    public void starting()
    {
        LMod model = new LMod(width, height, length, border);

        LCon controller = new LCon(model);

        LVie view = new LVie(model, controller);

        model.addObserver(view);

        (new Thread(model)).start();
    }
}
