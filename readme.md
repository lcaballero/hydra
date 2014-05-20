# Overview
Hydra is a code generator project in the spirit of Yeoman, though not as heavy handed as Maven Archetypes,
written in Java and with possibly a more arcane name.

The idea is that given some kind of seed structure and a bit of custom code this tool would be how future
project setup would be done.  No requirement exists to include scaffolding for things like sub-views
or plug-in(s) to existing project structures -- but we'll see how that goes.  Providing a examples and
guidelines for testing project generation are higher on the list.


## Target Process
- Use a generator-generator to start a new generator
- Withing that generator take advantage of core models and utilities to facilitate a new project template.
- Store templates in a local repo and generate templates for a variety of uses.


## Setup
At the moment the project is very young and is maintained as a working project here just for backup, and
to entertain anyone who is interested.  As of 5-19-2014 there is not a working example, but check back
in a few weeks if this is something you are interested in contributing to.


## TODO (Mostly considerations)

- Making target directories that don't exist in the source.
- Placing source files in different, or generated location (as per the above).
This will be necessary to expand a namespace from a '.' delimited name to a '/' file path delimited name.
- Reading source files from a zip
- Reading source files from a jar where the app is running. This would be useful for creating a fat jar with
all of the deps and then reading all source files from inside of that jar.

## Config

- Location of template repos (local)
- Location of ancillary file copy repo (for instance a repo of .gitignore files maintained outside of the template.
