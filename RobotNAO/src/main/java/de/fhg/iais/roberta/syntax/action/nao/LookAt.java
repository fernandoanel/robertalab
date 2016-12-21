package de.fhg.iais.roberta.syntax.action.nao;

import java.util.List;

import de.fhg.iais.roberta.blockly.generated.Block;
import de.fhg.iais.roberta.blockly.generated.Field;
import de.fhg.iais.roberta.blockly.generated.Value;
import de.fhg.iais.roberta.mode.action.nao.Frame;
import de.fhg.iais.roberta.syntax.BlockTypeContainer;
import de.fhg.iais.roberta.syntax.BlocklyBlockProperties;
import de.fhg.iais.roberta.syntax.BlocklyComment;
import de.fhg.iais.roberta.syntax.BlocklyConstants;
import de.fhg.iais.roberta.syntax.Phrase;
import de.fhg.iais.roberta.syntax.action.Action;
import de.fhg.iais.roberta.syntax.expr.Expr;
import de.fhg.iais.roberta.transformer.ExprParam;
import de.fhg.iais.roberta.transformer.Jaxb2AstTransformer;
import de.fhg.iais.roberta.transformer.JaxbTransformerHelper;
import de.fhg.iais.roberta.util.dbc.Assert;
import de.fhg.iais.roberta.visitor.AstVisitor;
import de.fhg.iais.roberta.visitor.NaoAstVisitor;

/**
 * This class represents the <b>naoActions_lookAt</b> block from Blockly into the AST (abstract syntax tree).
 * Object from this class will generate code for making the NAO look at a specific position.<br/>
 * <br/>
 * The client must provide the {@link Frame}, {@link lookX}, {@link lookY}, {@link lookZ} and {@link speed} (frame, coordinates and fraction of speed).
 */
public final class LookAt<V> extends Action<V> {

    private final Frame frame;
    private final Expr<V> lookX;
    private final Expr<V> lookY;
    private final Expr<V> lookZ;
    private final Expr<V> speed;

    private LookAt(Frame frame, Expr<V> lookX, Expr<V> lookY, Expr<V> lookZ, Expr<V> speed, BlocklyBlockProperties properties, BlocklyComment comment) {
        super(BlockTypeContainer.getByName("LOOK_AT"), properties, comment);
        Assert.notNull(frame, "Missing frame in LookAt block!");
        this.frame = frame;
        this.lookX = lookX;
        this.lookY = lookY;
        this.lookZ = lookZ;
        this.speed = speed;
        setReadOnly();
    }

    /**
     * Creates instance of {@link LookAt}. This instance is read only and can not be modified.
     *
     * @param frame {@link Frame} the coordinates relate to,
     * @param X {@link lookX} x coordinate,
     * @param Y {@link lookY} y coordinate,
     * @param Z {@link lookZ} z coordinate,
     * @param speed {@link speed} the movement will be executed at,
     * @param properties of the block (see {@link BlocklyBlockProperties}),
     * @param comment added from the user,
     * @return read only object of class {@link LookAt}
     */
    private static <V> LookAt<V> make(
        Frame frame,
        Expr<V> lookX,
        Expr<V> lookY,
        Expr<V> lookZ,
        Expr<V> speed,
        BlocklyBlockProperties properties,
        BlocklyComment comment) {
        return new LookAt<V>(frame, lookX, lookY, lookZ, speed, properties, comment);
    }

    public Frame getFrame() {
        return this.frame;
    }

    public Expr<V> getlookX() {
        return this.lookX;
    }

    public Expr<V> getlookY() {
        return this.lookY;
    }

    public Expr<V> getlookZ() {
        return this.lookZ;
    }

    public Expr<V> getSpeed() {
        return this.speed;
    }

    @Override
    public String toString() {
        return "LookAt [" + this.frame + ", " + this.lookX + ", " + this.lookY + ", " + this.lookZ + ", " + this.speed + "]";
    }

    @Override
    protected V accept(AstVisitor<V> visitor) {
        return ((NaoAstVisitor<V>) visitor).visitLookAt(this);
    }

    /**
     * Transformation from JAXB object to corresponding AST object.
     *
     * @param block for transformation
     * @param helper class for making the transformation
     * @return corresponding AST object
     */
    public static <V> Phrase<V> jaxbToAst(Block block, Jaxb2AstTransformer<V> helper) {
        List<Field> fields = helper.extractFields(block, (short) 1);
        List<Value> values = helper.extractValues(block, (short) 4);

        String frame = helper.extractField(fields, BlocklyConstants.DIRECTION);
        Phrase<V> lookX = helper.extractValue(values, new ExprParam(BlocklyConstants.X, Integer.class));
        Phrase<V> lookY = helper.extractValue(values, new ExprParam(BlocklyConstants.Y, Integer.class));
        Phrase<V> lookZ = helper.extractValue(values, new ExprParam(BlocklyConstants.Z, Integer.class));
        Phrase<V> speed = helper.extractValue(values, new ExprParam(BlocklyConstants.Speed, Integer.class));

        return LookAt.make(
            Frame.get(frame),
            helper.convertPhraseToExpr(lookX),
            helper.convertPhraseToExpr(lookY),
            helper.convertPhraseToExpr(lookZ),
            helper.convertPhraseToExpr(speed),
            helper.extractBlockProperties(block),
            helper.extractComment(block));
    }

    @Override
    public Block astToBlock() {
        Block jaxbDestination = new Block();
        JaxbTransformerHelper.setBasicProperties(this, jaxbDestination);

        JaxbTransformerHelper.addField(jaxbDestination, BlocklyConstants.DIRECTION, this.frame.toString());
        JaxbTransformerHelper.addValue(jaxbDestination, BlocklyConstants.X, this.lookX);
        JaxbTransformerHelper.addValue(jaxbDestination, BlocklyConstants.Y, this.lookY);
        JaxbTransformerHelper.addValue(jaxbDestination, BlocklyConstants.Z, this.lookZ);
        JaxbTransformerHelper.addValue(jaxbDestination, BlocklyConstants.Speed, this.speed);

        return jaxbDestination;
    }
}
