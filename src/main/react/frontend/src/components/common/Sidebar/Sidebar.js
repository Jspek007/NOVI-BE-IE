import React, {useState} from 'react';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Collapse from '@mui/material/Collapse';
import * as MdIcons from 'react-icons/md';
import {SidebarData} from "./SidebarData";
import {hasChildren} from "../../../utils/sidebarMenuUtil";
import styles from "./Sidebar.module.css";
import {Link} from "react-router-dom";
import {useNavigate} from "react-router";

export default function Sidebar() {
    return SidebarData.map((item, key) => (
            <>
                <section className={styles.sidebar_item_container}>
                    <MenuItem key={key} item={item}/>
                </section>
            </>
        )
    );
}

const MenuItem = ({item}) => {
    const Component = hasChildren(item) ? MultiLevel : SingleLevel;
    return <Component item={item}/>
}

const SingleLevel = ({item}) => {
    const navigate = useNavigate();
    const handleClick = () => {
        navigate(item.path);
    }


    return (
        <ListItem button link={item.path} onClick={handleClick}>
            <ListItemIcon>{item.icon}</ListItemIcon>
            <ListItemText primary={item.title}/>
        </ListItem>
    );
};

const MultiLevel = ({item}) => {
    const {items: children} = item;
    const [open, setOpen] = useState(false);

    const handleClick = () => {
        setOpen((prev) => !prev);
    };

    return (
        <>
            <ListItem button onClick={handleClick}>
                <ListItemIcon>{item.icon}</ListItemIcon>
                <ListItemText primary={item.title}/>
                {open ? <MdIcons.MdExpandLess/> : <MdIcons.MdExpandMore/>}
            </ListItem>
            <Collapse in={open} timeout="auto" unmountOnExit>
                <List component="div" disablePadding>
                    {children.map((child, key) => (
                        <Link to={child.to} className={styles.nav_item_link}>
                            <MenuItem key={key} item={child}/>
                        </Link>
                    ))}
                </List>
            </Collapse>
        </>
    )

}