package screen;

import java.awt.event.KeyEvent;

import engine.Login;
import engine.SoundEffect;
import engine.Cooldown;
import engine.Core;

import java.io.IOException;
import java.sql.*;
import java.util.*;


/**
 * Implements the title screen.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class LoginScreen extends Screen {

    private String ID;
    private String PASSWORD;
    private String USERNAME;
    private Cooldown SelectCooldown;
    private Login login;
    private Connection conn;

    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 200;

    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;

    /** For selection moving sound */
    private SoundEffect soundEffect;

    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width
     *            Screen width.
     * @param height
     *            Screen height.
     * @param fps
     *            Frames per second, frame rate at which the game is run.
     */
    public LoginScreen(Connection conn, Login login, final int width, final int height, final int fps) {

        super(width, height, fps);

        this.conn = conn;
        this.login = login;

        this.SelectCooldown = Core.getCooldown(200);
        this.SelectCooldown.reset();
        this.returnCode = 1;


        soundEffect = new SoundEffect();


    }

    /**
     * Starts the action.
     *
     * @return Next screen code.
     */
    public final int run() {
        super.run();

        return this.returnCode;
    }

    public final void initialize(){
        super.initialize();
        Scanner scanner = new Scanner(System.in);

        System.out.print("ID : ");
        this.ID = scanner.nextLine();

        System.out.print("PASSWORD: ");
        this.PASSWORD = scanner.nextLine();

        //data.displayPlayer(conn);

        if(login.loginCheck(conn,ID,PASSWORD)){
            //메인메뉴로 연결

        }
        else{
            //다시 입력 받기
        }

    }

    /**
     * Updates the elements on screen and checks for events.
     */
    protected final void update() {
        super.update();

        draw();
        //뒤로, 메인메뉴로 이동

    }



    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);


        drawManager.completeDrawing(this);
    }
}