#!/bin/sh
# One pass over where a symbol is declared, extended, wired through DI, referenced from XML and used,
# each section capped so a common name cannot flood the context. Text search, not a call graph.
set -eu
usage='usage: symbol.sh <Name> [path ...]   (default: app/src)'
name=${1:?$usage}
shift
case $name in [A-Za-z_]*) ;; *) echo "$usage" >&2; exit 2 ;; esac
case $name in *[!A-Za-z0-9_]*) echo "$usage" >&2; exit 2 ;; esac
cd "$(git rev-parse --show-toplevel)"
[ $# -gt 0 ] || set -- app/src
cap=15

section() {
  title=$1
  shift
  out=$("$@" 2>/dev/null || true)
  count=$(printf '%s\n' "$out" | grep -c . || true)
  echo "== $title ($count)"
  [ "$count" = 0 ] && return 0
  printf '%s\n' "$out" | head -n "$cap" | cut -c1-200
  if [ "$count" -gt "$cap" ]; then echo "... $((count - cap)) more"; fi
}
search() { grep -rnE --include='*.kt' --include='*.java' "$@" | sort -t: -k1,1 -k2,2n; }

di_search() {
  {
    grep -rnE --include='*.kt' --include='*.java' "(${name}::class|hiltViewModel<$name>)" "$@" 2>/dev/null || true
    find "$@" -type f \( -path '*/di/*.kt' -o -path '*/di/*.java' \) -exec grep -HnE "\\b$name\\b" {} + 2>/dev/null || true
  } | sort -u -t: -k1,1 -k2,2n
}

section declared search "(class|interface|object|fun|val|var|typealias) (<[^>]+> )?([A-Za-z0-9_<>?]+\\.)?$name\\b" "$@"
# "^) : X" is a multi-line constructor's supertype list; a multi-line function's return type is "): X".
section 'extended or implemented' search -e "(class|object|interface) [^{]*[:,] *$name\\b" -e "^ *\\) : ([^{=]*[ ,])?$name\\b" "$@"
section 'DI wiring' di_search "$@"
section 'XML references' grep -rnw --include='*.xml' "$name" "$@"
section 'files using it (hits per file)' sh -c 'grep -rcw --include="*.kt" --include="*.java" --include="*.xml" "$0" "$@" | grep -v ":0$" | sort -t: -k2 -rn' "$name" "$@"