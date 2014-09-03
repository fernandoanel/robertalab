package de.fhg.iais.roberta.ast.syntax.sensor;

import de.fhg.iais.roberta.ast.syntax.Phrase;
import de.fhg.iais.roberta.ast.visitor.AstVisitor;
import de.fhg.iais.roberta.dbc.Assert;

/**
 * This class represents the <b>robSensors_infrared_getMode</b>, <b>robSensors_infrared_getSample</b> and <b>robSensors_infrared_setMode</b> blocks from Blockly
 * into
 * the AST (abstract syntax
 * tree).
 * Object from this class will generate code for setting the mode of the sensor or getting a sample from the sensor.<br/>
 * <br>
 * The client must provide the {@link SensorPort} and {@link InfraredSensorMode}. See enum {@link InfraredSensorMode} for all possible modes of the sensor.<br>
 * <br>
 * To create an instance from this class use the method {@link #make(InfraredSensorMode, SensorPort)}.<br>
 */
public class InfraredSensor<V> extends Sensor<V> {
    private final InfraredSensorMode mode;
    private final SensorPort port;

    private InfraredSensor(InfraredSensorMode mode, SensorPort port, boolean disabled, String comment) {
        super(Phrase.Kind.INFRARED_SENSING, disabled, comment);
        Assert.isTrue(mode != null);
        this.mode = mode;
        this.port = port;
        setReadOnly();
    }

    /**
     * Create object of the class {@link InfraredSensor}.
     * 
     * @param mode in which the sensor is operating. See enum {@link InfraredSensorMode} for all possible modes that the sensor have.
     * @param port on where the sensor is connected. See enum {@link SensorPort} for all possible sensor ports
     * @param disabled state of the block,
     * @param comment added from the user
     * @return read only object of class {@link InfraredSensor}
     */
    public static <V> InfraredSensor<V> make(InfraredSensorMode mode, SensorPort port, boolean disabled, String comment) {
        return new InfraredSensor<V>(mode, port, disabled, comment);
    }

    /**
     * @return get the mode of sensor. See enum {@link InfraredSensorMode} for all possible modes that the sensor have.
     */
    public InfraredSensorMode getMode() {
        return this.mode;
    }

    /**
     * @return get the port on which the sensor is connected. See enum {@link SensorPort} for all possible sensor ports.
     */
    public SensorPort getPort() {
        return this.port;
    }

    @Override
    public String toString() {
        return "InfraredSensor [mode=" + this.mode + ", port=" + this.port + "]";
    }

    @Override
    protected V accept(AstVisitor<V> visitor) {
        return visitor.visitInfraredSensor(this);
    }

}
