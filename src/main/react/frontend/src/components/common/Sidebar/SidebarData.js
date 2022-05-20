import * as FaIcons from 'react-icons/fa';
import * as RiIcons from 'react-icons/ri';
import * as GrIcons from 'react-icons/gr';

export const SidebarData = [
    {
        title: 'Dashboard',
        path:'/admin',
        icon: <RiIcons.RiDashboardFill />,
        cName: 'dashboard'
    },
    {
        title: 'Sales',
        icon: <FaIcons.FaFileInvoiceDollar />,
        items: [
            {
                title: "Orders",
                to: "/admin/sales_orders"
            },
            {
                title: "Invoices",
                to: "/admin/invoices"
            },
            {
                title: "Creditmemos",
                to: "/admin/credits"
            }
        ]
    },
    {
        title: 'Customers',
        path: '/admin/customers',
        icon: <RiIcons.RiCustomerService2Fill/>,
        cName: 'customers'
    },
    {
        title: 'Catalog',
        icon: <GrIcons.GrCatalog />,
        items: [
            {
                title: "Products",
                to: "/admin/products"
            },
            {
                title: "Categories",
                to: "/admin/categories"
            }
        ]
    }
]
