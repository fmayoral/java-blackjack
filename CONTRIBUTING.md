# Contributing to Blackjack Game

## Table of Contents

- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Cloning the Repository](#cloning-the-repository)
    - [Creating a Branch](#creating-a-branch)
- [Making Changes](#making-changes)
    - [Coding Style](#coding-style)
- [Submitting a Pull Request](#submitting-a-pull-request)
- [Review Process](#review-process)

## Getting Started

### Prerequisites

- Make sure you have installed all required software and tools.

### Cloning the Repository

1. Clone the project to your local machine.

    ```bash
    git clone https://github.com/fmayoral/java-blackjack.git
    cd blackjack-service
    ```

### Creating a Branch

1. Create a new branch based on `master`:

**features** - adding new features
```bash
git checkout -b feature/my-feature-branch
```

**bugfix** - fixing breaking functionality
```bash
git checkout -b bugfix/my-bugfix-branch
```

**chore** - code cleanup/typos/minor fixes
```bash
git checkout -b chore/my-chore-branch
```

## Making Changes

### Coding Style

- Ensure you stick to the coding standards used throughout the project.

## Submitting a Merge Request

1. Commit your changes:

    ```bash
    git add .
    git commit -m "Add some feature or fix some bug"
    ```

2. Push your changes to your remote repository:

    ```bash
    git push origin my-feature-branch
    ```

3. Go to the project's GitLab page create a merge request.

## Review Process

- Once a Merge Request is opened, it will be reviewed by one or more maintainers.
- If changes are requested, please make the necessary changes and then re-submit the Pull Request.
- If your Pull Request is accepted, it will be merged into the main codebase, and you will be credited for your
  contributions.

Thank you for taking the time to contribute!
