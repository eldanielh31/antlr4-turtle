package com.nikoskatsanos.turtle;

import com.nikoskatsanos.antlrturtle.LogoBaseListener;
import com.nikoskatsanos.antlrturtle.LogoParser.*;

/**
 * A {@link LogoListener} is effectively a {@link LogoBaseListener}, receiving the callbacks from Antlr parser and delegating the commands to the corresponding {@code painter}
 */
public class LogoListener extends LogoBaseListener {

    private final TurtlePainter painter;

    public LogoListener(TurtlePainter painter) {
        this.painter = painter;
    }

    @Override
    public void exitForward(final ForwardContext ctx) {
        this.painter.forward(Integer.parseInt(ctx.getChild(1).getText()));
    }

    @Override
    public void exitBack(final BackContext ctx) {
        this.painter.back(Integer.parseInt(ctx.getChild(1).getText()));
    }

    @Override
    public void exitRight(final RightContext ctx) {
        this.painter.right(Integer.parseInt(ctx.getChild(1).getText()));
    }

    @Override
    public void exitLeft(final LeftContext ctx) {
        this.painter.left(Integer.parseInt(ctx.getChild(1).getText()));
    }

    @Override
    public void exitSet(final SetContext ctx) {
        final String[] point = ctx.POINT().getText().split(",");
        final int x = Integer.parseInt(point[0]);
        final int y = Integer.parseInt(point[1]);
        this.painter.set(x, y);
    }

    @Override
    public void exitPenUp(final PenUpContext ctx) {
        this.painter.penUp();
    }

    @Override
    public void exitPenDown(final PenDownContext ctx) {
        this.painter.penDown();
    }

    @Override
    public void exitClearscreen(ClearscreenContext ctx) {
        this.painter.cls();
    }

    @Override
    public void exitResetAngle(ResetAngleContext ctx) {
        this.painter.resetAngle();
    }

    @Override
    public void exitProg(ProgContext ctx) {
        this.painter.finish();
    }
}
