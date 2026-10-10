# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.2.0] - 2026-10-09

### Added

- Added `shutterup:shutters` and `shutterup:wooden_shutters` block and item tags.
- Added compatibility with Sable.
    - Shutters are now tagged as `sable:quarter_volume` and `sable:super_light`, like Trapdoors and Doors.

### Changed

- Shutters can now be placed horizontally against the side of a block by shift right-clicking, similar to Trapdoors.
    - Clicking the upper half places them upside down, and clicking the lower half places them on the floor.
- Shutters placed while powered by redstone now start open, like Trapdoors.

### Fixed

- Fixed Shutters toggling instead of placing a block when shift right-clicking a non-solid block in front of them, like
  making a double chest.
- Fixed Shutters toggling when interacting with blocks that have their own interactions, like Chests, in front of them.
- Fixed Shutters using Oak sounds instead of their material's sounds, like Iron, Bamboo, Cherry, and Nether wood.
- Fixed Iron Shutters having wooden properties, such as dropping without a Pickaxe.
- Fixed open Shutters showing dark shading on one panel when the other side is blocked.

## [1.1.1] - 2026-04-08

### Changed

- Adjusted Copper and Iron Shutter recipes.

## [1.1.0] - 2026-04-07

### Added

- Added Iron and Copper Shutters.

### Changed

- Shutters can now be placed sideways.
- Shutters connected vertically will now open together.
    - This can be bypassed by shift right-clicking to open them.
- Shutter recipes now output 4 Shutters instead of 2.
- Updated mod logo.

### Fixed

- Fixed missing subtitles for Shutters.
- Fixed closing sound sometimes not playing.

## [1.0.5] - 2026-02-04

### Changed

- Updated mod logo (thanks, Nekomaster!)