{
  description = "WiPro thesis dev shell (tectonic + biber-for-tectonic + make)";

  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixos-24.11";

  outputs = { self, nixpkgs }:
  let
    systems = [ "x86_64-linux" "aarch64-linux" ];
    forAll = f: builtins.listToAttrs (map (s: { name = s; value = f s; }) systems);
  in {
    devShells = forAll (system:
      let
        pkgs = import nixpkgs { inherit system; };
      in {
        default = pkgs.mkShell {
          # Tools available when you `nix develop`
          packages = [
            pkgs.gnumake
            pkgs.git
            pkgs.tectonic
            pkgs.biber-for-tectonic   # <-- per your request (2.17 on 24.11)
          ];

          # Keep cache tidy; not strictly required
          env = {
            TECTONIC_CACHE_DIR = "$HOME/.cache/Tectonic";
          };

          shellHook = ''
            echo "Dev shell: Tectonic + biber-for-tectonic"
            echo "which biber: $(which biber)"
            biber --version | head -n1 || true
            echo "which tectonic: $(which tectonic)"
            tectonic --version || true
            echo "Tip: run 'which -a biber' to ensure no other biber shadows this one."
          '';
        };
      });
  };
}
