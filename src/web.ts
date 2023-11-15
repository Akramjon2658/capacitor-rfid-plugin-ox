import { WebPlugin } from '@capacitor/core';

import type { RFIDPlugin } from './definitions';

export class RFIDWeb extends WebPlugin implements RFIDPlugin {
  startScan(): Promise<void> {
    return Promise.resolve();
  }

  stopScan(): Promise<void> {
    return Promise.resolve();
  }

  getScanData(): Promise<void> {
    return Promise.resolve();
  }

  async getOutputPower(): Promise<{ value: number }> {
    return { value: 0 };
  }

  async setOutputPower(options: { power: number }): Promise<{ value: number }> {
    return { value: options.power };
  }
}
