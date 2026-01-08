# RANDANDALG @ HSLU

> by Eldar Omerovic

---

## Getting Started on NixOS

First, clone this repository.

If you're using NixOS, just run `nix develop` within this project's root directory. After that, you can run `make` to build the documentation.

In the `build` folder you'll see a `main.pdf`. This is the built documentation which you can open with a `.pdf` viewer of your choice.

## More Information

If you're interested in building this yourself, here's some information on how its set up on nix.

This project is being built with:

- `tectonic` (TeX engine)
- `biber`\* (v2.17 for biblatex v3.17)
- `gnumake`
- `git`

\* biber is installed (on nixos) using the package `biber-for-tectonic` because its shipped with the compatible version for tectonic. See compatibility table, to find the version you might need: [Texdoc (on page 5)](https://texdoc.org/serve/biber.pdf/0)
