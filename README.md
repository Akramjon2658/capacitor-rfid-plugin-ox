# capacitor-rfid-plugin-ox

rfid reader for UROVO DT50

## Install

```bash
npm install capacitor-rfid-plugin-ox
npx cap sync
```

## API

<docgen-index>

* [`startScan()`](#startscan)
* [`stopScan()`](#stopscan)
* [`getScanData()`](#getscandata)
* [`getOutputPower()`](#getoutputpower)
* [`setOutputPower(...)`](#setoutputpower)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### startScan()

```typescript
startScan() => Promise<void>
```

--------------------


### stopScan()

```typescript
stopScan() => Promise<void>
```

--------------------


### getScanData()

```typescript
getScanData() => Promise<any>
```

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### getOutputPower()

```typescript
getOutputPower() => Promise<{ value: number; }>
```

**Returns:** <code>Promise&lt;{ value: number; }&gt;</code>

--------------------


### setOutputPower(...)

```typescript
setOutputPower(options: { power: number; }) => Promise<{ value: number; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ power: number; }</code> |

**Returns:** <code>Promise&lt;{ value: number; }&gt;</code>

--------------------

</docgen-api>
