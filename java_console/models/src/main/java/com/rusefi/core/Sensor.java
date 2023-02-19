package com.rusefi.core;

import com.rusefi.config.Field;
import com.rusefi.config.FieldType;
import com.rusefi.config.generated.EngineState;
import com.rusefi.config.generated.Fields;
import com.rusefi.config.generated.FuelComputer;
import com.rusefi.config.generated.TsOutputs;
import com.rusefi.sensor_logs.BinaryLogEntry;
import org.jetbrains.annotations.Nullable;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import static com.rusefi.config.generated.Fields.*;

/**
 * @author Andrey Belomutskiy
 * 2/11/13
 */
public enum Sensor implements BinaryLogEntry {
    /**
     * Please note that these enum names are used to make 'set_mock_XXX_voltage' commands
     */

    // RPM, vss
    RPMValue(GAUGE_NAME_RPM, SensorCategory.SENSOR_INPUTS, FieldType.UINT16, TsOutputs.RPMVALUE, 1, 0, 8000, "RPM"),
//    rpmAcceleration("dRPM", SensorCategory.SENSOR_INPUTS, FieldType.INT16, 6, 1.0, 0.0, 5.0, "RPM/s"),
//    speedToRpmRatio("Gearbox Ratio", SensorCategory.SENSOR_INPUTS, FieldType.INT16, 8, 0.01, 0.0, 0.0, "value"),
    vehicleSpeedKph("Vehicle Speed", SensorCategory.SENSOR_INPUTS, FieldType.INT8, TsOutputs.VEHICLESPEEDKPH, 1.0, 0.0, 0.0, "kph"),

    // Temperatures
    INT_TEMP(GAUGE_NAME_CPU_TEMP, SensorCategory.OPERATIONS, FieldType.INT8, TsOutputs.INTERNALMCUTEMPERATURE, 1, 0, 5, "C"),
    CLT(GAUGE_NAME_CLT, SensorCategory.SENSOR_INPUTS, FieldType.INT16, TsOutputs.COOLANT, 1.0 / PACK_MULT_TEMPERATURE, -40, 150, "deg C"),
    IAT(GAUGE_NAME_IAT, SensorCategory.SENSOR_INPUTS, FieldType.INT16, TsOutputs.INTAKE, 1.0 / PACK_MULT_TEMPERATURE, -40, 150, "deg C"),
    AuxFreq1("AuxFreq1", SensorCategory.SENSOR_INPUTS, FieldType.INT16, TsOutputs.AUXSPEED1, 1, -40, 150, "Hz"),
    AuxFreq2("AuxFreq2", SensorCategory.SENSOR_INPUTS, FieldType.INT16, TsOutputs.AUXSPEED2, 1, -40, 150, "Hz"),
//    AuxT1("AuxT1", SensorCategory.SENSOR_INPUTS, FieldType.INT16, 16, 1.0 / PACK_MULT_TEMPERATURE, -40, 150, "deg C"),
//    AuxT2("AuxT2", SensorCategory.SENSOR_INPUTS, FieldType.INT16, 18, 1.0 / PACK_MULT_TEMPERATURE, -40, 150, "deg C"),

    // throttle, pedal
    TPS(GAUGE_NAME_TPS, SensorCategory.SENSOR_INPUTS, FieldType.INT16, TsOutputs.TPSVALUE, 1.0 / PACK_MULT_PERCENT, 0, 100, "%"), // throttle position sensor
    PPS(GAUGE_NAME_THROTTLE_PEDAL, SensorCategory.SENSOR_INPUTS, FieldType.INT16, TsOutputs.THROTTLEPEDALPOSITION, 1.0 / PACK_MULT_PERCENT, 0, 100, "%"), // pedal position sensor

    // air flow/mass measurement
    MAF_RAE(GAUGE_NAME_MAF + " raw", SensorCategory.SENSOR_INPUTS, FieldType.UINT16, TsOutputs.RAWMAF, 1.0 / PACK_MULT_VOLTAGE, 0, 5, "Volts"),
    MAF(GAUGE_NAME_MAF, SensorCategory.SENSOR_INPUTS, FieldType.UINT16, TsOutputs.MAFMEASURED, 1.0 / PACK_MULT_MASS_FLOW, 0, 5, "Volts"),
    MAP(GAUGE_NAME_MAP, SensorCategory.SENSOR_INPUTS, FieldType.UINT16, TsOutputs.MAPVALUE, 1.0 / PACK_MULT_PRESSURE, 20, 300, "kPa"),

    Lambda(GAUGE_NAME_LAMBDA, SensorCategory.SENSOR_INPUTS, FieldType.UINT16, TsOutputs.LAMBDAVALUE, 1.0 / PACK_MULT_LAMBDA, 0.65, 1.2, "lambda"),

    VBATT(GAUGE_NAME_VBAT, SensorCategory.SENSOR_INPUTS, FieldType.UINT16, TsOutputs.VBATT, 1.0 / PACK_MULT_VOLTAGE, 4, 18, "Volts"),
//    oilPressure(GAUGE_NAME_OIL_PRESSURE, SensorCategory.SENSOR_INPUTS, FieldType.INT16, 40, 1.0 / PACK_MULT_PRESSURE, 0, 5, "X"),
//    vvtPositionB1I(GAUGE_NAME_VVT_B1I, SensorCategory.SENSOR_INPUTS, FieldType.INT16, 42, 1.0 / PACK_MULT_ANGLE, 0, 5, "deg"),
//
//    // fuel math
//    CHARGE_AIR_MASS("airmass", SensorCategory.OPERATIONS, FieldType.UINT16, 44, 0.001, 0, 3, "g/cyl"),
    crankingFuel(GAUGE_NAME_FUEL_CRANKING, SensorCategory.FUEL, FieldType.UINT16, EngineState.CRANKINGFUEL_BASEFUEL, 1, 0, 30, "ms"),
    baseFuel(Fields.GAUGE_NAME_FUEL_BASE, SensorCategory.FUEL, FieldType.UINT16, TsOutputs.BASEFUEL, 1.0 / PACK_MULT_MS, 0, 30, "ms"),
    runningFuel(GAUGE_NAME_FUEL_RUNNING, SensorCategory.FUEL, FieldType.UINT16, TsOutputs.FUELRUNNING, 1.0 / PACK_MULT_FUEL_MASS, 0, 15, "ms"),
//    actualLastInjection(GAUGE_NAME_FUEL_LAST_INJECTION, SensorCategory.FUEL, FieldType.UINT16, 54, 1.0 / PACK_MULT_MS, 0, 30, "ms"),
//    injectorDutyCycle(Fields.GAUGE_NAME_FUEL_INJ_DUTY, SensorCategory.FUEL, FieldType.UINT8, 56, 0.5, 0, 100, "%"),
    veValue(GAUGE_NAME_FUEL_VE, SensorCategory.FUEL, FieldType.UINT8, TsOutputs.VEVALUE, 0.5, 0, 100, "%"),
    // injectionOffset
    tCharge(GAUGE_NAME_TCHARGE, SensorCategory.FUEL, FieldType.FLOAT, TsOutputs.TCHARGE, 1.0 / PACK_MULT_TEMPERATURE, 30, 140, "C"),
//
//    // Corrections
//    injectorLagMs(GAUGE_NAME_INJECTOR_LAG, SensorCategory.FUEL, FieldType.UINT16, 62, 1.0 / PACK_MULT_MS, 0, 15, "ms"),
//    iatCorrection(GAUGE_NAME_FUEL_IAT_CORR, SensorCategory.FUEL, FieldType.INT16, TsOutputs.IAT, 1.0 / PACK_MULT_PERCENT, 0, 5, "ratio"),
//    cltCorrection(GAUGE_NAME_FUEL_CLT_CORR, SensorCategory.FUEL, FieldType.INT16, 66, 1.0 / PACK_MULT_PERCENT, 0, 5, "ratio"),
//    fuelPidCorrection("Fuel PID", SensorCategory.FUEL, FieldType.INT16, 70, 1.0 / PACK_MULT_MS, -2, 2, "ms"),
//
//    // Wall model AE
//    wallFuelAmount(GAUGE_NAME_FUEL_WALL_AMOUNT, SensorCategory.FUEL, FieldType.UINT16, 72, 1.0 / PACK_MULT_MS, 0, 20, "ms"),
//    wallFuelCorrection(GAUGE_NAME_FUEL_WALL_CORRECTION, SensorCategory.FUEL, FieldType.INT16, 74, 0.001, -5, 5, "ms"),
//
//    // TPS/load AE
//    engineLoadAccelDelta("load accel delta", SensorCategory.FUEL, FieldType.INT16, 76, 1.0 / PACK_MULT_PERCENT, -5, 5, "ratio"),
//    deltaTps(Fields.GAUGE_NAME_FUEL_TPS_ROC, SensorCategory.FUEL, FieldType.INT16, 78, 1.0 / PACK_MULT_PERCENT, -100, 100, "%"),
    tpsAccelFuel(Fields.GAUGE_NAME_FUEL_TPS_EXTRA, SensorCategory.FUEL, FieldType.INT16, TsOutputs.TPSACCELFUEL, 1.0 / PACK_MULT_MS, 0, 200, "ms"),
//
//    // Ignition
//    ignitionAdvance("ignition timing", SensorCategory.OPERATIONS, FieldType.INT16, 84, 1.0 / PACK_MULT_ANGLE, 30, 140, "deg"),
//    DWELL(Fields.GAUGE_COIL_DWELL_TIME, SensorCategory.OPERATIONS, FieldType.UINT16, 86, 1.0 / PACK_MULT_MS, 1, 10, "ms"),
//    coilDutyCycle(Fields.GAUGE_NAME_DWELL_DUTY, SensorCategory.OPERATIONS, FieldType.UINT16, 88, 1.0 / PACK_MULT_PERCENT, 0, 100, "%"),
//
//    // Idle & ETB
//    idlePosition("Idle Position", SensorCategory.OPERATIONS, FieldType.INT16, 90, 1.0 / PACK_MULT_PERCENT, 0, 100, "%"),
    etbTarget(GAUGE_NAME_ETB_TARGET, SensorCategory.OTHERS, FieldType.INT16, TsOutputs.ETBTARGET, 1.0 / PACK_MULT_PERCENT, 0, 100, "%"),
    etb1DutyCycle(GAUGE_NAME_ETB_DUTY, SensorCategory.OTHERS, FieldType.INT16, TsOutputs.ETB1DUTYCYCLE, 1.0 / PACK_MULT_PERCENT, 0, 100, "%"),
//    etb1Error(GAUGE_NAME_ETB_ERROR, SensorCategory.OTHERS, FieldType.INT16, 96, 1.0 / PACK_MULT_PERCENT, 0, 100, "%"),

    boostDcPidOutput("Boost PID output", SensorCategory.FUEL, FieldType.INT16, TsOutputs.BOOSTSTATUS_OUTPUT, 1.0 / 100, 0, 20, ""),
    boostDcPidPTerm("Boost PID pTerm", SensorCategory.FUEL, FieldType.FLOAT, TsOutputs.BOOSTSTATUS_PTERM, 1.0 / 100, 0, 20, ""),
    boostDcPidITerm("Boost PID iTerm", SensorCategory.FUEL, FieldType.INT16, TsOutputs.BOOSTSTATUS_ITERM, 1.0 / 100, 0, 20, ""),

    // Fuel system
    afrTarget(GAUGE_NAME_TARGET_AFR, SensorCategory.FUEL, FieldType.INT16, FuelComputer.TARGETAFR, 1.0 / 1000, 0, 20, ""),
    sdAirMassInOneCylinder("sdAirMassInOneCylinder", SensorCategory.FUEL, FieldType.FLOAT, FuelComputer.SDAIRMASSINONECYLINDER, 1.0, 0, 15, "g"),

    // Knock
//    knockLevel(GAUGE_NAME_KNOCK_LEVEL, SensorCategory.DEBUG, FieldType.FLOAT, 108, 0, 5),
//
    // Mode, firmware, protocol, run time
    TIME_SECONDS(GAUGE_NAME_TIME, SensorCategory.OPERATIONS, FieldType.INT, TsOutputs.SECONDS, 1, 0, 5, ""),
//    engineMode("mode", SensorCategory.OPERATIONS, FieldType.INT, 116, 0, 5),
    FIRMWARE_VERSION(GAUGE_NAME_VERSION, SensorCategory.OPERATIONS, FieldType.INT, TsOutputs.FIRMWAREVERSION, 1, 0, 100, "version_f"),
    TS_CONFIG_VERSION(".ini version", SensorCategory.OPERATIONS, FieldType.INT, TsOutputs.TSCONFIGVERSION),

//    engineMakeCodeNameCrc16("engine crc16", SensorCategory.STATUS, FieldType.UINT16, 138, 0, 5),
    // Errors
    totalTriggerErrorCounter(GAUGE_NAME_TRG_ERR, SensorCategory.STATUS, FieldType.INT, TsOutputs.TOTALTRIGGERERRORCOUNTER, 0, 5),
    lastErrorCode(GAUGE_NAME_WARNING_LAST, SensorCategory.STATUS, FieldType.INT, TsOutputs.LASTERRORCODE, 0, 5),

    // Debug
    debugFloatField1(GAUGE_NAME_DEBUG_F1, SensorCategory.DEBUG, FieldType.FLOAT, TsOutputs.DEBUGFLOATFIELD1, 0, 5),
    debugFloatField2(GAUGE_NAME_DEBUG_F2, SensorCategory.DEBUG, FieldType.FLOAT, TsOutputs.DEBUGFLOATFIELD2, 0, 5),
    debugFloatField3(GAUGE_NAME_DEBUG_F3, SensorCategory.DEBUG, FieldType.FLOAT, TsOutputs.DEBUGFLOATFIELD3, 0, 5),
    debugFloatField4(GAUGE_NAME_DEBUG_F4, SensorCategory.DEBUG, FieldType.FLOAT, TsOutputs.DEBUGFLOATFIELD4, 0, 5),
    debugFloatField5(GAUGE_NAME_DEBUG_F5, SensorCategory.DEBUG, FieldType.FLOAT, TsOutputs.DEBUGFLOATFIELD5, 0, 5),
    debugFloatField6(GAUGE_NAME_DEBUG_F6, SensorCategory.DEBUG, FieldType.FLOAT, TsOutputs.DEBUGFLOATFIELD6, 0, 5),
    debugFloatField7(GAUGE_NAME_DEBUG_F7, SensorCategory.DEBUG, FieldType.FLOAT, TsOutputs.DEBUGFLOATFIELD7, 0, 5),
    debugIntField1(GAUGE_NAME_DEBUG_I1, SensorCategory.DEBUG, FieldType.INT, TsOutputs.DEBUGINTFIELD1, 0, 5),
    debugIntField2(GAUGE_NAME_DEBUG_I2, SensorCategory.DEBUG, FieldType.INT, TsOutputs.DEBUGINTFIELD2, 0, 5),
    debugIntField3(GAUGE_NAME_DEBUG_I3, SensorCategory.DEBUG, FieldType.INT, TsOutputs.DEBUGINTFIELD3, 0, 5),
    debugIntField4("debug i4", SensorCategory.DEBUG, FieldType.INT16, TsOutputs.DEBUGINTFIELD4, 0, 5),
    debugIntField5("debug i5", SensorCategory.DEBUG, FieldType.INT16, TsOutputs.DEBUGINTFIELD5, 0, 5),
//    accelerationX(GAUGE_NAME_ACCEL_X, SensorCategory.SENSOR_INPUTS, FieldType.INT16, 212, 1.0 / PACK_MULT_PERCENT, -3, 3, "G"),
//    accelerationY(GAUGE_NAME_ACCEL_Y, SensorCategory.SENSOR_INPUTS, FieldType.INT16, 214, 1.0 / PACK_MULT_PERCENT, -3, 3, "G"),

    tuneCrc16("tune crc16", SensorCategory.STATUS, FieldType.UINT16, TsOutputs.TUNECRC16, 0, 5),

    // Raw sensors
    rawClt("raw CLT", SensorCategory.SENSOR_INPUTS, FieldType.INT16, TsOutputs.RAWCLT, 1.0 / PACK_MULT_VOLTAGE, 0, 5, "volts"),
    rawIat("raw IAT", SensorCategory.SENSOR_INPUTS, FieldType.INT16, TsOutputs.RAWIAT, 1.0 / PACK_MULT_VOLTAGE, 0, 5, "volts"),

//    tcuDesiredGear(GAUGE_NAME_DESIRED_GEAR, SensorCategory.SENSOR_INPUTS, FieldType.INT8, 262, 1.0, 0, 100, "gear"),
//    flexPercent(GAUGE_NAME_FLEX, SensorCategory.SENSOR_INPUTS, FieldType.INT8, 263, 1.0 / 2, 0, 100, "%"),

    wastegatePosition(GAUGE_NAME_WG_POSITION, SensorCategory.SENSOR_INPUTS, FieldType.INT16, TsOutputs.WASTEGATEPOSITIONSENSOR, 1.0 / PACK_MULT_PERCENT, 0, 100, "%"),
    idlePositionSensor(GAUGE_NAME_IDLE_POSITION, SensorCategory.SENSOR_INPUTS, FieldType.INT16, TsOutputs.IDLEPOSITIONSENSOR, 1.0 / PACK_MULT_PERCENT, 0, 100, "%"),

    lowFuelPressure(GAUGE_NAME_FUEL_PRESSURE_LOW, SensorCategory.OPERATIONS, FieldType.INT16, TsOutputs.RAWLOWFUELPRESSURE, 1.0 / PACK_MULT_PRESSURE, 10, 20, "afr"),
    highFuelPressure(GAUGE_NAME_FUEL_PRESSURE_HIGH, SensorCategory.OPERATIONS, FieldType.INT16, TsOutputs.HIGHFUELPRESSURE, 1.0 / PACK_MULT_HIGH_PRESSURE, 10, 20, "afr"),


//    airFuelRatio(GAUGE_NAME_AFR, SensorCategory.OPERATIONS, FieldType.INT16, 282, 1.0 / PACK_MULT_AFR, 10, 20, "afr"),
//    airFuelRatio2(GAUGE_NAME_AFR2, SensorCategory.OPERATIONS, FieldType.INT16, 288, 1.0 / PACK_MULT_AFR, 10, 20, "afr"),

    vvtPositionB1E(GAUGE_NAME_VVT_B1E, SensorCategory.SENSOR_INPUTS, FieldType.INT16, TsOutputs.VVTPOSITIONB1E, 1.0 / PACK_MULT_ANGLE, 0, 5, "deg"),
    vvtPositionB2I(GAUGE_NAME_VVT_B2I, SensorCategory.SENSOR_INPUTS, FieldType.INT16, TsOutputs.VVTPOSITIONB2I, 1.0 / PACK_MULT_ANGLE, 0, 5, "deg"),
    vvtPositionB2E(GAUGE_NAME_VVT_B2E, SensorCategory.SENSOR_INPUTS, FieldType.INT16, TsOutputs.VVTPOSITIONB2E, 1.0 / PACK_MULT_ANGLE, 0, 5, "deg"),

    vvtTargetB1I(GAUGE_NAME_VVT_TARGET_B1I, SensorCategory.SENSOR_INPUTS, FieldType.INT8, TsOutputs.VVTTARGETS1, 1, -50, 50, "deg"),
    vvtTargetB1E(GAUGE_NAME_VVT_TARGET_B1E, SensorCategory.SENSOR_INPUTS, FieldType.INT8, TsOutputs.VVTTARGETS2, 1, -50, 50, "deg"),
    vvtTargetB2I(GAUGE_NAME_VVT_TARGET_B2I, SensorCategory.SENSOR_INPUTS, FieldType.INT8, TsOutputs.VVTTARGETS3, 1, -50, 50, "deg"),
    vvtTargetB2E(GAUGE_NAME_VVT_TARGET_B2E, SensorCategory.SENSOR_INPUTS, FieldType.INT8, TsOutputs.VVTTARGETS4, 1, -50, 50, "deg"),
    turboSpeed(GAUGE_NAME_TURBO_SPEED, SensorCategory.SENSOR_INPUTS, FieldType.INT16, TsOutputs.TURBOSPEED, 1, -50, 50, "hz"),

//    accelerationZ(GAUGE_NAME_ACCEL_Z, SensorCategory.SENSOR_INPUTS, FieldType.INT16, 308, 1.0 / PACK_MULT_PERCENT, -3, 3, "G"),
//    accelerationRoll(GAUGE_NAME_ACCEL_ROLL, SensorCategory.SENSOR_INPUTS, FieldType.INT16, 310, 1.0 / PACK_MULT_PERCENT, -30, 30, "deg/s"),
//    accelerationYaw(GAUGE_NAME_ACCEL_YAW, SensorCategory.SENSOR_INPUTS, FieldType.INT16, 312, 1.0 / PACK_MULT_PERCENT, -30, 30, "deg/s"),

    instantMAP("Instant " + GAUGE_NAME_MAP, SensorCategory.SENSOR_INPUTS, FieldType.UINT16, TsOutputs.INSTANTMAPVALUE, 1.0 / PACK_MULT_PRESSURE, 20, 300, "kPa"),


//    baseDwell("baseDwell", SensorCategory.SENSOR_INPUTS, FieldType.INT, 972, 1.0, -1.0, -1.0, ""),
//    dwellVoltageCorrection("dwellVoltageCorrection", SensorCategory.SENSOR_INPUTS, FieldType.INT, 976, 1.0, -1.0, -1.0, ""),
//    luaTimingAdd("luaTimingAdd", SensorCategory.SENSOR_INPUTS, FieldType.INT, 980, 1.0, -1.0, -1.0, ""),
//    luaTimingMult("luaTimingMult", SensorCategory.SENSOR_INPUTS, FieldType.INT, 984, 1.0, -1.0, -1.0, ""),
//    etb_idlePosition("ETB idlePosition", SensorCategory.SENSOR_INPUTS, FieldType.INT, 988, 1.0, -1.0, -1.0, ""),
//    trim("trim", SensorCategory.SENSOR_INPUTS, FieldType.INT, 992, 1.0, -1.0, -1.0, ""),
//    luaAdjustment("luaAdjustment", SensorCategory.SENSOR_INPUTS, FieldType.INT, 996, 1.0, -1.0, -1.0, ""),

    LUAGAUGE1("Lua gauge 1", SensorCategory.SENSOR_INPUTS, FieldType.FLOAT, TsOutputs.LUAGAUGES1, 1, 4, 18000, "value"),
    LUAGAUGE2("Lua gauge 2", SensorCategory.SENSOR_INPUTS, FieldType.FLOAT, TsOutputs.LUAGAUGES2, 1, 4, 18000, "value"),

    // Synthetic (console only) channels
    ETB_CONTROL_QUALITY("ETB metric", SensorCategory.SNIFFING, "", 100),
    ;

    private final String name;
    private final SensorCategory category;
    private final String units;
    private final double minValue;
    private final double maxValue;
    @Nullable
    private final FieldType type;
    private final int offset;
    private final double scale;

    static {
        Set<String> NAMES = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

        for (Sensor s : Sensor.values()) {
            if (!NAMES.add(s.name))
                throw new IllegalArgumentException("Unique name expected " + s.name);
        }
    }

    Sensor(String name, SensorCategory category, FieldType type, Field field, double scale, double minValue, double maxValue, String units) {
        this.name = name == null ? name() : name;
        this.type = type;
        this.offset = field.getTotalOffset();
        this.scale = scale;
        this.category = category;
        this.units = units;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    Sensor(String name, SensorCategory category, FieldType type, Field field, double minValue, double maxValue) {
        this(name, category, type, field, 1.0, minValue, maxValue, "n/a");
    }

    Sensor(String name, SensorCategory category, FieldType type, Field field) {
        this(name, category, type, field, 0, 100);
    }

    /**
     * This constructor is used for virtual sensors which do not directly come from ECU
     */
    Sensor(String name, SensorCategory category, String units, double maxValue) {
        this(name, category, units, 0, maxValue);
    }

    /**
     * This constructor is used for virtual sensors which do not directly come from ECU
     */
    Sensor(String name, SensorCategory category, String units, double minValue, double maxValue) {
        this.name = name;
        this.category = category;
        this.units = units;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.scale = 1.0;
        type = null;
        offset = -1;
    }



    public static Collection<Sensor> getSensorsForCategory(String category) {
        final Set<Sensor> sensors = new TreeSet<>(Comparator.comparing(o -> o.getName().toLowerCase()));

        for (final Sensor sensor : values()) {
            if (sensor.category.getName().equals(category)) {
                sensors.add(sensor);
            }
        }

        return sensors;
    }

    public static double processAdvance(double advance) {
        return advance > 360 ? advance - 720 : advance;
    }

    public static Sensor lookup(String gaugeName, Sensor defaultValue) {
        Sensor sensor;
        try {
            sensor = valueOf(Sensor.class, gaugeName);
        } catch (IllegalArgumentException e) {
            sensor = defaultValue;
        }
        return sensor;
    }

    public static Sensor find(String value) {
        for (Sensor s : values())
            if (s.name.equals(value) || s.name().equals(value))
                return s;
        throw new IllegalStateException("Sensor not found: " + value);
    }

    public double getValueForChannel(ByteBuffer bb) {
        switch (getType()) {
            case FLOAT:
                return bb.getFloat();
            case INT:
                return bb.getInt();
            case UINT16:
                // no cast - we want to discard sign
                return bb.getInt() & 0xFFFF;
            case INT16:
                // cast - we want to retain sign
                return  (short)(bb.getInt() & 0xFFFF);
            case UINT8:
                // no cast - discard sign
                return bb.getInt() & 0xFF;
            case INT8:
                // cast - retain sign
                return (byte)(bb.getInt() & 0xFF);
            default:
                throw new UnsupportedOperationException("type " + getType());
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUnit() {
        return units;
    }

    @Override
    public int getByteSize() {
        switch (getType()) {
            case UINT8:
                return 0;
            case INT8:
                return 1;
            case UINT16:
                return 2;
            case INT16:
                return 3;
            case INT:
                return 4;
            case FLOAT:
                return 7;
            default:
                throw new UnsupportedOperationException("" + getType());
        }
    }

    // TODO: this should be a string
    @Override
    public SensorCategory getCategory() {
        return category;
    }

    public String getUnits() {
        return units;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public int getOffset() {
        return offset;
    }

    public double getScale() {
        return scale;
    }

    public FieldType getType() {
        return type;
    }

    public double translateValue(double value) {
        return value;
    }

    @Override
    public void writeToLog(DataOutputStream dos, double value) throws IOException {
        switch (type) {
            case INT8:
            case UINT8:
                dos.write((int) value);
                return;
            case FLOAT:
                dos.writeFloat((float) value);
                return;
            case UINT16:
            case INT16:
                dos.writeShort((int) value);
                return;
            case INT:
                dos.writeInt((int) value);
                return;
            default:
                throw new UnsupportedOperationException("Type " + type);
        }
    }

    public String getLogValue(double value) {
        if (scale == 1 && type != null) {
            // only handle sensors without scale, i.e. not packed floats
            switch (type) {
                case UINT16: {
                    int v = ((int) value) & 0xFFFF;
                    return Integer.toString(v);
                }
            }

        }

        return Double.toString(value);
    }

    static {
        if (TS_FILE_VERSION_OFFSET != TsOutputs.TSCONFIGVERSION.getOffset())
            throw new IllegalStateException("static assert failed TS_FILE_VERSION_OFFSET");
    }
}
