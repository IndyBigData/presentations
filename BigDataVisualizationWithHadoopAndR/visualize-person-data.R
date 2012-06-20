# Pull in our data
people <- read.table("~/person-data.tsv", sep="\t", quote="", col.names=c("name", "height", "year", "place"), stringsAsFactors=F)

people$year.number <- as.numeric(people$year)

# Let's pull height information
library("stringr")
computeHeight <- function(strHeight) {
  m <- str_match(strHeight, "^(\\d)\\s*('|ft)\\s*(\\d+)")
  if(!is.na(m[1])) {
    return(as.numeric(m[2]) + as.numeric(m[4])/12)
  }
  m <- str_match(strHeight, "^(\\d\\.?\\d*)\\s*m")
  if(!is.na(m[1])) {
    return(as.numeric(m[2]) * 3.2808399)
  }
  m <- str_match(strHeight, "^(\\d),(\\d+)\\s*m")
  if(!is.na(m[1])) {
    return((as.numeric(m[2]) + as.numeric(paste("0.", m[3], sep=""))) * 3.2808399)
  }
  m <- str_match(strHeight, "^(\\d{3,})\\s*cm")
  if(!is.na(m[1])) {
    return(as.numeric(m[2]) * 0.032808399)
  }
  return(0)
}
people[ncol(people), "height.computed"] <- NA
for(i in 1:nrow(people)){
  people[i,]$height.computed <- computeHeight(people[i,]$height)
}

# Let's pull birthplace info
people[ncol(people), "country"] <- NA
for(i in 1:nrow(people)){
  p <- people[i,]$place
  if(is.na(str_locate(p, ",")[1])) {
    people[i,]$country <- p
  }
  else {
    m <- str_match(p, ".*,\\s*([^,]+?)$")
    if(!is.na(m[1])) {
      people[i,]$country <- m[2]
    }
    else {
      people[i,]$country <- NA
    }
  }
}

# Normalize country names just a little bit
people$country <- ifelse(test=people$country == "U.S.", yes="United States", no=people$country)
people$country <- ifelse(test=people$country == "USA", yes="United States", no=people$country)
people$country <- ifelse(test=people$country == "United States of America", yes="United States", no=people$country)
people$country <- ifelse(test=people$country == "UK", yes="United Kingdom", no=people$country)
people$country <- ifelse(test=people$country == "U.K.", yes="United Kingdom", no=people$country)
people$country <- ifelse(test=people$country == "UK.", yes="United Kingdom", no=people$country)
people$country <- ifelse(test=people$country == "England", yes="United Kingdom", no=people$country)

# Look at "valid" birth years
people.valid.age <- subset(people, year.number > 99)

# Let's draw a histogram of all the people where we have birth year info
hist(2012-as.numeric(people.valid.age$year), col="red", xlab="Age", main="Ages of People in Freebase Dataset")
axis(side=1, at=seq(0,200,25), labels=seq(0,200,25))

# Let's look at valid heights
people.valid.height <- subset(people, height.computed > 1 & height.computed < 9)

# Let's plot a height distribution
hist(people.valid.height$height.computed, col="red", xlab="Height (ft)", main="Heights of People in Freebase Dataset")
axis(side=1, at=seq(0,7,0.5), labels=seq(0,7,0.5))

# Now let's look at both sets of data together
floor10 <- function(num) {
  return(floor(num/10) * 10)
}
people.valid <- subset(people, height.computed > 1 & height.computed < 9 & year.number > 1940)
people.valid$height.floor <- factor(floor(people.valid$height.computed))
people.valid[ncol(people.valid), "age.floor"] <- NA
for(i in 1:nrow(people.valid)) {
  people.valid[i,]$age.floor <- floor10(2012-people.valid[i,]$year.number)
}

library("ggplot2")
qplot(year.number, data=people.valid, fill=height.floor, geom="bar", xlab="Birth Year", ylab="Number of People", main="Freebase Persons' Birth Years and Heights") + scale_fill_hue(name = "Height (ft)")

# Or, let's see if the number of reported heights changes over time
qplot(year.number, data=people.valid, geom="freqpoly", group=height.floor, colour=height.floor, position="identity", xlab="Birth Year", ylab="Number of People", main="Freebase Persons' Birth Years and Heights") + scale_colour_discrete(name = "Height (ft)")
aggs <- aggregate(people.valid$year.number, by=list(year = people.valid$year.number, height = people.valid$height.floor), FUN=length)
aggs[ncol(aggs), "pct"] <- NA
for(i in 1:nrow(aggs)){
  aggs[i,]$pct <- aggs[i,]$x/sum(subset(aggs, year==aggs[i,]$year)$x)*100
}

# Here's a stacked area for composition comparison
qplot(year, pct, data=aggs, geom="area", fill=height, xlab="Birth Year", ylab="Percentage", main="Freebase Persons' Birth Years and Heights") + scale_fill_discrete(name="Height (ft)")

# OR (while hard to read), a scatter w/ fit lines
aggs.56 <- subset(aggs, height == 5 | height == 6)
qplot(year, pct, data=aggs.56, geom=c("smooth", "point"), colour=height, xlab="Birth Year", ylab="Percentage", main="Freebase Persons' Birth Years and Heights") + scale_colour_discrete(name="Height (ft)")

# How about a treemap?
library("portfolio")
people.valid$height.floor.numeric <- as.numeric(as.character(people.valid$height.floor))
map.market(id=people.valid$name, area=people.valid$height.floor.numeric^3, group=people.valid$country, color=people.valid$age.floor, main="Freebase Persons Map")