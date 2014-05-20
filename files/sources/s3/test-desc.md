# Overview

The source here is for testing general copy and delete of a directory and it's content.
Normally the shell takes care of the details of emptying out a directory prior to removing
the target directory itself.  However, in the code we must traverse the file structure
and remove all the files and then remove the directory.

Additionally we must copy all of the directories and then all of the files.

These traversals amount to a pre (copy) and post (delete) order traversal -- see the code.