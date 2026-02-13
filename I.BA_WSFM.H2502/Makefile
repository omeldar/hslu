# Makefile
MAIN=main
OUTDIR=build

.PHONY: all pdf clean

all: pdf

$(OUTDIR):
	mkdir -p $(OUTDIR)

pdf: $(OUTDIR)
	tectonic -X compile $(MAIN).tex \
	  --outdir $(OUTDIR) --keep-intermediates --reruns 0
	biber --input-directory $(OUTDIR) --output-directory $(OUTDIR) $(MAIN)
	tectonic -X compile $(MAIN).tex \
	  --outdir $(OUTDIR)

clean:
	rm -rf $(OUTDIR)
