import { WebPlugin } from '@capacitor/core';

import type { RFIDPlugin } from './definitions';

export class RFIDWeb extends WebPlugin implements RFIDPlugin {
  isConnected(): Promise<{ connected: boolean }> {
    return Promise.resolve({ connected: false });
  }

  startScan(): Promise<void> {
    return Promise.resolve();
  }

  stopScan(): Promise<void> {
    return Promise.resolve();
  }

  clearData(): Promise<void> {
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

  async getRange(): Promise<{ value: number }> {
    return { value: 0 };
  }

  async setRange(options: { range: number }): Promise<{ value: number }> {
    return { value: options.range };
  }


  async getQueryMode(): Promise<{ value: 0 | 1 | 2 | 3 }> {
    return { value: 0 };
  }

  async setQueryMode(options: { queryMode: 0 | 1 | 2 | 3 }): Promise<{ value: number }> {
    return { value: options.queryMode };
  }

  async getReaderType(): Promise<{ value: number }> {
    return { value: 0 };
  }

  async getFirmwareVersion(): Promise<{ value: string }> {
    return { value: 'not implemented' };
  }

  async writeEpc(options: { epc: string, password?: string }): Promise<{ value: number }> {
    console.log(options.epc)

    return { value: - 1 }
  }

  async writeEpcString(options: { epc: string, password?: string }): Promise<{ value: number }> {
    console.log(options.epc)

    return { value: - 1 }
  }
}
