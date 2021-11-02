# 1. Classes That Should Be Private

## Status

accepted, not implemented

## Context

It would be nice to provide functions for bot implementers that allows them to simulate game to make informed decision.

It will be problematic to reuse game code because it would require to care about compatibility of most of the project code.

## Decision

Internal symbols should be made private.

Encourage bot implementers to copy simulation code or implement special simulation interface.

Maybe somday I will implement this.

## Consequences

The project will be easier to maintain and refacotor.

Most of the changes would not break compatibility.
