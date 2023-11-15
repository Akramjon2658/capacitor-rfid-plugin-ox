import { Plugin } from '@capacitor/core'

export interface RFIDPlugin extends Plugin {
  startScan(): Promise<void>;
  stopScan(): Promise<void>;

  getScanData(): Promise<any>;
  getOutputPower(): Promise<{ value: number }>;
  setOutputPower(options: { power: number }): Promise<{ value: number }>;
}