// @ts-check
const { test, expect } = require('@playwright/test');
const utils = require('./utils');

test.describe('Dashboard', () => {
  // login
  test.beforeAll(async ({ browser }) => {
    const context = await browser.newContext();
    const page = await context.newPage();

    await page.goto('/login');
    await page.fill('input[name="username"]', utils.USERNAME);
    await page.fill('input[name="password"]', "123");
    await page.getByRole('button', { name: 'Submit' }).click();

    await page.waitForURL('/dashboard');
    await expect(page).toHaveTitle(/AfP MyPlatform/);
    await expect(page.locator('text="Status"')).toBeVisible();
  });

  test('Products', async ({ page }) => {
    await page.goto('/products');
    await expect(page.locator('text="My Products"')).toBeVisible();
  });

  test('Transactions', async ({ page }) => {
    await page.goto('/transactions');
    await expect(page.locator('text="My Transactions"')).toBeVisible();
  });

});