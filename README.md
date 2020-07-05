# TalentRecruiter

A backend service that handles a (very simple) recruiting process. The process
consist two types of objects: job offers and applications from candidates. Below are the
required fields:

Offer:
jobTitle (unique)
startDate
numberOfApplications


Application:
related offer
candidate email (unique per Offer)
resume text
applicationStatus (APPLIED, INVITED, REJECTED, HIRED)

