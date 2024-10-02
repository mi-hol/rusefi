//
// Created by kifir on 9/30/24.
//

#include "pch.h"

#include "shift_torque_reduction_test_base.h"
#include "engine_configuration_defaults.h"

ShiftTorqueReductionTestConfig ShiftTorqueReductionTestConfig::setTorqueReductionEnabled(
    const std::optional<bool> value
) {
    m_isTorqueReductionEnabled = value;
    return *this;
}

ShiftTorqueReductionTestConfig ShiftTorqueReductionTestConfig::setTorqueReductionActivationMode(
    const std::optional<torqueReductionActivationMode_e> value
) {
    m_torqueReductionActivationMode = value;
    return *this;
}

ShiftTorqueReductionTestConfig ShiftTorqueReductionTestConfig::setTriggerPin(
    const std::optional<switch_input_pin_e> value
) {
    m_torqueReductionTriggerPin = value;
    return *this;
}

ShiftTorqueReductionTestConfig ShiftTorqueReductionTestConfig::setPinInverted(const std::optional<bool> value) {
    m_pinInverted = value;
    return *this;
}

ShiftTorqueReductionTestConfig ShiftTorqueReductionTestConfig::setLaunchActivatePin(
    const std::optional<switch_input_pin_e> value
) {
    m_launchActivatePin = value;
    return *this;
}

ShiftTorqueReductionTestConfig ShiftTorqueReductionTestConfig::setLaunchActivateInverted(
    const std::optional<bool> value
) {
    m_launchActivateInverted = value;
    return *this;
}

void ShiftTorqueReductionTestBase::setUpTestConfig(const ShiftTorqueReductionTestConfig& config) {
    configureTorqueReductionEnabled(config.getTorqueReductionEnabled());
    configureTorqueReductionActivationMode(config.getTorqueReductionActivationMode());
    configureTorqueReductionButton(config.getTriggerPin());
    configureTorqueReductionButtonInverted(config.getPinInverted());
    configureLaunchActivatePin(config.getLaunchActivatePin());
    configureLaunchActivateInverted(config.getLaunchActivateInverted());
}

void ShiftTorqueReductionTestBase::configureTorqueReductionEnabled(const std::optional<bool> torqueReductionEnabled) {
    if (torqueReductionEnabled.has_value()) {
        engineConfiguration->torqueReductionEnabled = torqueReductionEnabled.value();
    } else {
        ASSERT_EQ(
            engineConfiguration->torqueReductionEnabled,
            engine_configuration_defaults::ENABLE_SHIFT_TORQUE_REDUCTION
        ); // check default value
    }
}

void ShiftTorqueReductionTestBase::configureTorqueReductionActivationMode(
    const std::optional<torqueReductionActivationMode_e> activationMode
) {
    if (activationMode.has_value()) {
        engineConfiguration->torqueReductionActivationMode = activationMode.value();
    } else {
        ASSERT_EQ(
            engineConfiguration->torqueReductionActivationMode,
            engine_configuration_defaults::TORQUE_REDUCTION_ACTIVATION_MODE
        ); // check default value
    }
}

void ShiftTorqueReductionTestBase::configureTorqueReductionButton(const std::optional<switch_input_pin_e> pin) {
    if (pin.has_value()) {
        engineConfiguration->torqueReductionTriggerPin = pin.value();
    } else {
        ASSERT_EQ(
            engineConfiguration->torqueReductionTriggerPin,
            engine_configuration_defaults::TORQUE_REDUCTION_TRIGGER_PIN
        ); // check default value
    }
}

void ShiftTorqueReductionTestBase::configureTorqueReductionButtonInverted(const std::optional<bool> pinInverted) {
    if (pinInverted.has_value()) {
        engineConfiguration->torqueReductionTriggerPinInverted = pinInverted.value();
    } else {
        ASSERT_EQ(
            engineConfiguration->torqueReductionTriggerPinInverted,
            engine_configuration_defaults::TORQUE_REDUCTION_TRIGGER_PIN_INVERTED
        ); // check default value
    }
}

void ShiftTorqueReductionTestBase::configureLaunchActivatePin(const std::optional<switch_input_pin_e> pin) {
    if (pin.has_value()) {
        engineConfiguration->launchActivatePin = pin.value();
    } else {
        ASSERT_EQ(
            engineConfiguration->launchActivatePin,
            engine_configuration_defaults::LAUNCH_ACTIVATE_PIN
        ); // check default value
    }
}

void ShiftTorqueReductionTestBase::configureLaunchActivateInverted(const std::optional<bool> pinInverted) {
    if (pinInverted.has_value()) {
        engineConfiguration->launchActivateInverted = pinInverted.value();
    } else {
        ASSERT_EQ(
            engineConfiguration->launchActivateInverted,
            engine_configuration_defaults::LAUNCH_ACTIVATE_PIN_INVERTED
        ); // check default value
    }
}