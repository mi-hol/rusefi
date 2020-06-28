// this section was generated automatically by rusEfi tool ConfigDefinition.jar based on (unknown script) integration/trigger_central.txt Sun Jun 28 14:15:18 UTC 2020
// by class com.rusefi.output.CHeaderConsumer
// begin
#ifndef CONTROLLERS_GENERATED_TRIGGER_CENTRAL_GENERATED_H
#define CONTROLLERS_GENERATED_TRIGGER_CENTRAL_GENERATED_H
#include "rusefi_types.h"
#define HW_EVENT_TYPES 6
// start of trigger_central_s
struct trigger_central_s {
	trigger_central_s();
	/**
	 * Counter of hardware events since ECU start
	 * offset 0
	 */
	int hwEventCounters[HW_EVENT_TYPES];
	/**
	 * offset 24
	 */
	int vvtCamCounter = (int)0;
	/**
	 * offset 28
	 */
	int vvtEventRiseCounter = (int)0;
	/**
	 * offset 32
	 */
	int vvtEventFallCounter = (int)0;
	/** total size 36*/
};

typedef struct trigger_central_s trigger_central_s;

#endif
// end
// this section was generated automatically by rusEfi tool ConfigDefinition.jar based on (unknown script) integration/trigger_central.txt Sun Jun 28 14:15:18 UTC 2020
