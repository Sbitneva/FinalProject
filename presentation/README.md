# Project presentation

## Requirements

- git
- pandoc
- reveal.js

## Build

Clone _reveal.js_ and run _pandoc_

```bash
git clone https://github.com/hakimel/reveal.js

pandoc -t revealjs -s -o presentation.html presentation.md
```

## Run

Just open in default browser

```bash
xdg-open presentation.html
```