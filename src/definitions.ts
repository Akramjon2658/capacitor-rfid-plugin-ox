import type { Plugin } from '@capacitor/core';

export interface RFIDPlugin extends Plugin {
  isConnected(): Promise<{ connected: boolean }>;

  startScan(): Promise<void>;
  stopScan(): Promise<void>;
  clearData(): Promise<void>;

  getScanData(): Promise<any>;
  getOutputPower(): Promise<{ value: number }>;
  setOutputPower(options: { power: number }): Promise<{ value: number }>;

  getRange(): Promise<{ value: number }>;
  setRange(options: { range: number }): Promise<{ value: number }>;

  getQueryMode(): Promise<{ value: 0 | 1 | 2 | 3 }>;
  setQueryMode(options: {
    queryMode:
      | 0 // epc
      | 1 // epc+tid
      | 2 // epc+user
      | 3; // fasttid
  }): Promise<{ value: number }>;

  getReaderType(): Promise<{ value: number }>; // 80 - short, others - long distance mode
  getFirmwareVersion(): Promise<{ value: string }>;

  writeEpc(options: {
    epc: string;
    password?: string;
  }): Promise<{ value: number }>;
  writeEpcString(options: {
    epc: string;
    password?: string;
  }): Promise<{ value: number }>;

  startSearch(options: { searchableTags: string[], playSound: boolean }): Promise<void>;
  stopSearch(): Promise<void>;
}
