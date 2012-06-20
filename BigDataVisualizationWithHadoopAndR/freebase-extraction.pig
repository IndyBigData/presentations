REGISTER /some/path/pig-udfs.jar;
log = LOAD '/user/username/freebase-wex-2012-06-09-articles.tsv'
AS (wpid:int, name:chararray, last_updated:chararray, xml:chararray, text:chararray);
sanitized  = FOREACH log GENERATE name, REPLACE(REPLACE(xml, '<xhtml:br.+?\\/>', ''), '\\\\n', '') as xml;
first = FOREACH sanitized GENERATE name, xml, com.chacha.pig.XPathValue(xml, '//param[@name="birth_place"]') as birth_place, com.chacha.pig.XPathValue(xml, '//param[@name="height"]') as height;
filtered = FILTER first BY birth_place != '' AND height != '';
text = FOREACH filtered GENERATE name, height, com.chacha.pig.XPathValue(xml, '//param[@name="birth_date"]//param[@name="1"]') as birth_year, birth_place;
STORE text INTO 'freebase-extract';
