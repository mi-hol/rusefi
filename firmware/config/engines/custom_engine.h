/**
 * @file	custom_engine.h
 *
 * @date Jan 18, 2015
 * @author Andrey Belomutskiy, (c) 2012-2020
 */

#pragma once

#include "engine_configuration.h"
void setFrankensoConfiguration();
void setFrankenso_01_LCD();
void disableLCD();
void runSchedulingPrecisionTestIfNeeded(void);
void setDiscoveryPdm();
void setFrankensoBoardTestConfiguration();
void setEtbTestConfiguration();
void setTle8888TestConfiguration();
void setL9779TestConfiguration();

void setEepromTestConfiguration();

void mreBoardNewTest();
void mreSecondaryCan();
void mreBCM();

/**
 * PROTEUS_QC_TEST_BOARD
 * set engine_type 42
 */
void proteusBoardTest();
void proteusLuaDemo();
void proteusHarley();
void proteusDcWastegateTest();

void setTest33816EngineConfiguration();
void setBoschHDEV_5_injectors();

void setRotary();
void setVrThresholdTest();

void detectBoardType();
