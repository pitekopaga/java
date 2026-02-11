# Task-Automater
Automated recursive workflows
<!-- AI-powered web scraping tool for image collection -->

# Task-Automater

Final project for the Building AI course

## Summary

An intelligent web scraping tool that automates repetitive image collection workflows from authenticated websites. It handles login authentication, navigates pages, captures screenshots, and saves files with custom naming conventions based on webpage content.

## Background

Which problems does your idea solve? How common or frequent is this problem? What is your personal motivation? Why is this topic important or interesting?

* **Manual repetitive work** - Eliminates hours of clicking, screenshotting, and saving images one by one
* **Human error in file naming** - Prevents inconsistent naming conventions and typos when saving hundreds of files
* **Authentication barriers** - Handles login requirements that prevent simple scraping tools from working
* **Scale limitations** - Allows processing thousands of images instead of being limited by human endurance
* **Time-sensitive data collection** - Captures content before it expires, gets deleted, or access is revoked

This problem is extremely common across many industries. Digital marketers collect competitor assets, researchers archive web content, e-commerce teams gather product images, and content creators build reference libraries. The personal motivation comes from experiencing the frustration of spending entire days doing mindless clicking, knowing a computer could do it in minutes. Data preservation and efficient content management are crucial in our digital age, especially as more valuable content moves behind authentication walls.

## How is it used?

The user configures the tool through a simple interface by entering the target website URL, login credentials, and defining image selection criteria. They set up custom naming conventions using variables from the webpage and specify the save location. The tool launches a browser session, handles login automatically, navigates through pages, identifies target images, captures screenshots, and saves them with the defined naming pattern.

**Situations where the solution is needed:**
* Time-critical scenarios before content expires or access is revoked
* After-hours processing during off-peak times to avoid website traffic limits
* Bulk data migration when moving between platforms
* Compliance deadlines for legal or regulatory evidence preservation
* Research phases with tight project timelines

**Target users and their needs:**
* **Digital Marketers/Agencies** - Need competitor analysis tools with batch processing and organized filing
* **Researchers/Academics** - Require reliable, repeatable processes with detailed logging and citation-friendly naming
* **Legal Professionals** - Need audit trails, timestamp documentation, and secure storage
* **E-commerce Teams** - Require high-volume processing with quality consistency
* **Content Creators** - Need workflow integration with flexible organization options

<img src="docs/images/workflow-diagram.png" width="600">

Configuration example:
```json
{
  "website": "https://example.com/gallery",
  "login": {
    "username": "your-username",
    "password": "your-password"
  },
  "selectors": {
    "imageContainer": ".gallery-item",
    "imageElement": "img",
    "titleElement": ".image-title"
  },
  "naming": {
    "pattern": "{title}_{date}_{index}",
    "extension": ".png"
  },
  "output": {
    "directory": "./downloads",
    "createSubfolders": true
  }
}
```

## Data sources and AI methods

**Data Sources:**
* Web content from authenticated websites (user-provided credentials)
* DOM elements and metadata extracted using CSS selectors
* Image files accessed through browser automation
* User-defined configuration parameters

**AI Methods:**
* **Computer Vision** - Automated image detection and classification using OpenCV
* **Natural Language Processing** - Intelligent text extraction from webpage elements for file naming
* **Machine Learning** - Pattern recognition for identifying similar image types across different websites
* **Browser Automation** - Selenium/Playwright for intelligent navigation and interaction

| Technology | Purpose |
| ----------- | ----------- |
| Selenium/Playwright | Browser automation and authentication |
| OpenCV | Image detection and processing |
| BeautifulSoup | HTML parsing and element extraction |
| Pandas | Data organization and export |

[Selenium WebDriver Documentation](https://selenium-python.readthedocs.io/)
[Playwright Documentation](https://playwright.dev/python/)

## Challenges

What does your project _not_ solve? Which limitations and ethical considerations should be taken into account when deploying a solution like this?

**Technical Limitations:**
* Cannot bypass sophisticated anti-bot detection systems (Cloudflare, reCAPTCHA)
* Struggles with heavily JavaScript-dependent dynamic content loading
* Performance depends on website response times and internet connection
* May break when websites update their HTML structure

**Ethical Considerations:**
* Must respect website terms of service and robots.txt files
* Should implement rate limiting to avoid overloading servers
* Cannot be used for copyright infringement or unauthorized content access
* Requires explicit user permission for accessing private accounts
* Should not be used to collect personal information or violate privacy

**Legal Compliance:**
* Users must ensure they have proper authorization to access content
* Tool includes compliance mode with built-in rate limiting
* Provides audit logs for legal documentation purposes

## What next?

How could your project grow and become something even more? What kind of skills, what kind of assistance would you need to move on?

**Potential Growth Areas:**
* **AI-Powered Content Classification** - Automatically categorize and tag images using computer vision
* **Cloud Integration** - Direct upload to Google Drive, Dropbox, or AWS S3
* **Multi-Site Orchestration** - Manage scraping jobs across multiple websites simultaneously
* **API Development** - Provide programmatic access for integration with other tools
* **Browser Extension** - One-click scraping directly from the browser
* **Mobile App** - Monitor and control scraping jobs remotely

**Skills Needed:**
* Advanced machine learning for better image recognition
* Cloud infrastructure expertise (AWS, Docker, Kubernetes)
* Mobile development (React Native, Flutter)
* Database design for large-scale data management
* Security expertise for handling credentials safely

**Assistance Required:**
* UI/UX designer for better user interface
* DevOps engineer for scalable deployment
* Legal advisor for compliance framework
* Beta testers from target industries

## Acknowledgments

* [Selenium Project](https://github.com/SeleniumHQ/selenium) for browser automation framework / [Apache License 2.0](https://github.com/SeleniumHQ/selenium/blob/trunk/LICENSE)
* [Playwright](https://github.com/microsoft/playwright-python) by Microsoft for modern browser automation / [Apache License 2.0](https://github.com/microsoft/playwright-python/blob/main/LICENSE)
* [OpenCV](https://github.com/opencv/opencv-python) for computer vision capabilities / [Apache License 2.0](https://github.com/opencv/opencv/blob/4.x/LICENSE)
* Building AI course materials for project structure and methodology
* Open source community for web scraping best practices and ethical guidelines
* Beta testers who provided feedback on user experience and feature requirements
