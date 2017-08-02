# MacOKiller
Parse Mach-O or fat file in iOS/Mac OS, extract classes and methods.

## manual

#### For Mach-O file: 
`MachO macho = new MachOParser(filePath, 0).parseMach_O();`

#### For Fat file:
`Fat fat = new FatParser().getFatInfo(filePath);`

```
MachO and Fat see com.ssca.format.(MachO.java/Fat.java) for details.
```
