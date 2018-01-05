package robotx.modules;

/**
 * Created by Adithya on 1/5/2018.
 */

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotx.libraries.XOpMode;

public class JV_Lift_System extends XOpMode
{
    DcMotor liftMotor;

    double power = 0.5;

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
        if (xGamepad1.dpad_up.isDown())
        {
            liftMotor.setPower(power);

        }

        //Down
        else if (xGamepad1.dpad_down.isDown())
        {
            liftMotor.setPower(-power);
        }

        else
        {
            liftMotor.setPower(0.0);
        }
    }
    @Override
    public void stop() {
        super.stop();
    }
}

