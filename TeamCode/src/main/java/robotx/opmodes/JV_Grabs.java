package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XGamepad;
import robotx.libraries.XOpMode;


/**
 * Created by Adithya on 1/5/2018.
 */
@TeleOp(name = "JV_Grabs", group = "Default")


public class JV_Grabs extends XOpMode {
    Servo leftServo;
    Servo rightServo;

    @Override
    public void init()
    {
        super.init();
        rightServo = hardwareMap.servo.get("rightServo");
        leftServo = hardwareMap.servo.get("leftServo");
    }

    @Override
    public void init_loop() {
        super.init_loop();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop()
    {
        super.loop();
        if (xGamepad2.x.isDown())       //x will open the claw all the way
        {
            rightServo.setPosition(0.85);
            leftServo.setPosition(0.1);
        }

        else if (xGamepad2.y.isDown()) //y will set it to the optimal, middle position
        {
            rightServo.setPosition(0.4);
            leftServo.setPosition(0.5);

        }

        else if (xGamepad2.b.isDown())  //b will close the claw all the way
        {
            rightServo.setPosition(0.0);
            leftServo.setPosition(0.9);
        }
    }
    
    @Override
    public void stop() {
        super.stop();
    }
}
