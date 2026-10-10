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