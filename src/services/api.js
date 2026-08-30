const BASE_URL = 'http://localhost:8080/api';

export const api = {
  // Batches / Purchases
  getBatches: async () => {
    const res = await fetch(`${BASE_URL}/batches`);
    if (!res.ok) throw new Error('Failed to fetch batches');
    return res.json();
  },
  createBatch: async (batchData) => {
    const res = await fetch(`${BASE_URL}/batches`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(batchData),
    });
    if (!res.ok) throw new Error('Failed to create batch');
    return res.json();
  },

  // Sales
  getSales: async () => {
    const res = await fetch(`${BASE_URL}/sales`);
    if (!res.ok) throw new Error('Failed to fetch sales');
    return res.json();
  },
  createSale: async (saleData) => {
    const res = await fetch(`${BASE_URL}/sales`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(saleData),
    });
    if (!res.ok) throw new Error('Failed to create sale');
    return res.json();
  },
  updatePaymentStatus: async (invoiceNo, isPaid) => {
    const res = await fetch(`${BASE_URL}/sales/${invoiceNo}/payment-status`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ status: isPaid ? 'PAID' : 'PENDING' }),
    });
    if (!res.ok) throw new Error('Failed to update payment status');
    return res.json();
  }
};