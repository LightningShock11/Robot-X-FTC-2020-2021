package robotx.opmodes;

/**
 * Created by Adithya on 1/5/2018.
 */

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XGamepad;
import robotx.libraries.XOpMode;

@TeleOp(name = "JV_Lift_System", group = "Default")

public class JV_Lift_System extends XOpMode
{
    DcMotor liftMotor;

    double power2 = 1.0;

    @Override
    public void init()
    {
        super.init();
        liftMotor = hardwareMap.dcMotor.get("liftMotor");
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

        //Up
        if (xGamepad2.dpad_up.isDown())
        {
            liftMotor.setPower(power2);

        }

        //Down
        else if (xGamepad2.dpad_down.isDown())
        {
            liftMotor.setPower(-power2);
        }

        else
        {
            liftMotor.setPower(0.0);
        }
    }
    @Override
    public void stop()
    {
        super.stop();
    }
}




