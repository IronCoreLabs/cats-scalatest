{
  description = "Provision a dev environment";

  inputs = {
    typelevel-nix.url = "github:typelevel/typelevel-nix";
    nixpkgs.follows = "typelevel-nix/nixpkgs";
    flake-utils.follows = "typelevel-nix/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils, typelevel-nix }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = import nixpkgs {
          inherit system;
          overlays = [ typelevel-nix.overlays.default ];
        };

        mkShell = jdk: pkgs.devshell.mkShell {
          imports = [ typelevel-nix.typelevelShell ];
          name = "cats-scalatest";
          typelevelShell = {
            jdk.package = jdk;
            nodejs.enable = true;
          };
        };
      in
      {
        devShells = rec {
          default = temurin21;
          temurin21 = mkShell pkgs.temurin-bin-21;
          temurin25 = mkShell pkgs.temurin-bin-25;
        };
      }
    );
}
